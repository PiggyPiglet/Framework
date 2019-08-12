package me.piggypiglet.framework;

import com.google.inject.Injector;
import me.piggypiglet.framework.guice.modules.BindingSetterModule;
import me.piggypiglet.framework.guice.modules.InitialModule;
import me.piggypiglet.framework.registerables.StartupRegisterable;
import me.piggypiglet.framework.registerables.startup.CommandsRegisterable;
import me.piggypiglet.framework.registerables.startup.ImplementationFinderRegisterable;
import me.piggypiglet.framework.registerables.startup.ShutdownHookRegisterable;
import me.piggypiglet.framework.registerables.startup.ShutdownRegisterablesRegisterable;
import me.piggypiglet.framework.registerables.startup.file.FileTypesRegisterable;
import me.piggypiglet.framework.registerables.startup.file.FilesRegisterable;
import me.piggypiglet.framework.utils.annotations.Addon;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
final class FrameworkBootstrap {
    private final Framework config;

    FrameworkBootstrap(Framework config) {
        this.config = config;

        start();
    }

    private void start() {
        final AtomicReference<Injector> injector = new AtomicReference<>(new InitialModule(config).createInjector());
        final List<Class<? extends StartupRegisterable>> registerables = new ArrayList<>();

        Stream.of(
                ImplementationFinderRegisterable.class,
                FileTypesRegisterable.class,
                FilesRegisterable.class,
                ShutdownRegisterablesRegisterable.class,
                ShutdownHookRegisterable.class
        ).forEach(registerables::add);

        registerables.addAll(config.getStartupRegisterables());
        registerables.add(CommandsRegisterable.class);

        injector.get().getInstance(Reflections.class).getTypesAnnotatedWith(Addon.class).stream()
                .map(c -> c.getAnnotation(Addon.class))
                .map(Addon::value)
                .map(Arrays::stream)
                .forEach(s -> s.filter(r -> !registerables.contains(r)).forEach(registerables::add));

        registerables.forEach(r -> {
            StartupRegisterable registerable = injector.get().getInstance(r);
            registerable.run(injector.get());

            if (registerable.getProviders().size() > 0 || registerable.getAnnotatedBindings().size() > 0 || registerable.getStaticInjections().size() > 0) {
                injector.set(injector.get().createChildInjector(new BindingSetterModule(
                        registerable.getProviders(),
                        registerable.getAnnotatedBindings(),
                        registerable.getStaticInjections().toArray(new Class[]{})
                )));
            }
        });
    }
}