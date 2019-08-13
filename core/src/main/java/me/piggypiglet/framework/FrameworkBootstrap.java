package me.piggypiglet.framework;

import com.google.inject.Injector;
import me.piggypiglet.framework.guice.modules.BindingSetterModule;
import me.piggypiglet.framework.guice.modules.InitialModule;
import me.piggypiglet.framework.registerables.StartupRegisterable;
import me.piggypiglet.framework.registerables.startup.ImplementationFinderRegisterable;
import me.piggypiglet.framework.registerables.startup.ShutdownHookRegisterable;
import me.piggypiglet.framework.registerables.startup.ShutdownRegisterablesRegisterable;
import me.piggypiglet.framework.registerables.startup.commands.CommandsRegisterable;
import me.piggypiglet.framework.registerables.startup.file.FileTypesRegisterable;
import me.piggypiglet.framework.registerables.startup.file.FilesRegisterable;
import me.piggypiglet.framework.utils.annotations.Addon;
import org.reflections.Reflections;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class FrameworkBootstrap {
    private AtomicReference<Injector> injector;
    private final Set<StartupRegisterable> registerables = new LinkedHashSet<>();

    private final Framework config;

    public FrameworkBootstrap(Framework config) {
        this.config = config;

        start();
    }

    private void start() {
        injector = new AtomicReference<>(new InitialModule(this, config).createInjector());
        final Set<Class<? extends StartupRegisterable>> registerables = new LinkedHashSet<>();

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

            this.registerables.add(registerable);
        });
    }

    public Injector getInjector() {
        return injector.get();
    }

    public Set<StartupRegisterable> getRegisterables() {
        return registerables;
    }
}