package me.piggypiglet.framework;

import com.google.inject.Injector;
import me.piggypiglet.framework.guice.modules.BindingSetterModule;
import me.piggypiglet.framework.guice.modules.InitialModule;
import me.piggypiglet.framework.registerables.StartupRegisterable;
import me.piggypiglet.framework.registerables.startup.*;
import org.reflections.Reflections;

import java.util.ArrayList;
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
                FilesRegisterable.class,
                CommandsRegisterable.class,
                ShutdownRegisterablesRegisterable.class,
                ShutdownHookRegisterable.class
        ).forEach(registerables::add);

        registerables.addAll(config.getStartupRegisterables());
        injector.get().getInstance(Reflections.class).getSubTypesOf(StartupRegisterable.class).stream().filter(r -> !registerables.contains(r)).forEach(registerables::add);

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