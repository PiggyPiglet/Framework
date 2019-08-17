package me.piggypiglet.framework;

import com.google.inject.Injector;
import me.piggypiglet.framework.guice.modules.BindingSetterModule;
import me.piggypiglet.framework.guice.modules.InitialModule;
import me.piggypiglet.framework.reflection.Reflections;
import me.piggypiglet.framework.registerables.StartupRegisterable;
import me.piggypiglet.framework.registerables.startup.ImplementationFinderRegisterable;
import me.piggypiglet.framework.registerables.startup.ManagersRegisterable;
import me.piggypiglet.framework.registerables.startup.ShutdownHookRegisterable;
import me.piggypiglet.framework.registerables.startup.ShutdownRegisterablesRegisterable;
import me.piggypiglet.framework.registerables.startup.commands.CommandHandlerRegisterable;
import me.piggypiglet.framework.registerables.startup.commands.CommandsRegisterable;
import me.piggypiglet.framework.registerables.startup.file.FileTypesRegisterable;
import me.piggypiglet.framework.registerables.startup.file.FilesRegisterable;
import me.piggypiglet.framework.utils.annotations.addon.Addon;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class FrameworkBootstrap {
    private AtomicReference<Injector> injector;
    private final Set<StartupRegisterable> registerables = new LinkedHashSet<>();
    private final Set<Addon> addons = new LinkedHashSet<>();

    private final Framework config;

    public FrameworkBootstrap(Framework config) {
        this.config = config;

        start();
    }

    private void start() {
        injector = new AtomicReference<>(new InitialModule(this, config).createInjector());
        final Set<Class<? extends StartupRegisterable>> registerables = new LinkedHashSet<>();

        addons.addAll(injector.get().getInstance(Reflections.class).getTypesAnnotatedWith(Addon.class).stream()
                .map(c -> c.getAnnotation(Addon.class))
                .collect(Collectors.toSet()));

        Stream.of(
                ImplementationFinderRegisterable.class,
                FileTypesRegisterable.class,
                FilesRegisterable.class
        ).forEach(registerables::add);

        registerables.addAll(config.getStartupRegisterables());

        Stream.of(
                CommandsRegisterable.class,
                CommandHandlerRegisterable.class
        ).forEach(registerables::add);

        addons.stream()
                .map(Addon::startup)
                .map(Arrays::stream)
                .forEach(s -> s.filter(r -> !registerables.contains(r)).forEach(registerables::add));

        Stream.of(
                ManagersRegisterable.class,
                ShutdownRegisterablesRegisterable.class,
                ShutdownHookRegisterable.class
        ).forEach(registerables::add);

        registerables.forEach(r -> {
            StartupRegisterable registerable = injector.get().getInstance(r);
            registerable.run(injector.get());

            if (registerable.getBindings().size() > 0 || registerable.getAnnotatedBindings().size() > 0 || registerable.getStaticInjections().size() > 0) {
                injector.set(injector.get().createChildInjector(new BindingSetterModule(
                        registerable.getBindings(),
                        registerable.getAnnotatedBindings(),
                        registerable.getStaticInjections().toArray(new Class[]{})
                )));
            }

            this.registerables.add(registerable);
        });
    }

    /**
     * Get an instance of the current injector, as the injectable injector isn't updated through child injectors.
     * @return Injector instance
     */
    public Injector getInjector() {
        return injector.get();
    }

    /**
     * Get all the startup registerable instances that were initialized during startup.
     * @return Set of StartupRegisterable instances
     */
    public Set<StartupRegisterable> getRegisterables() {
        return registerables;
    }

    /**
     * Get all found addons and their data.
     * @return Set of Addon annotation data objects.
     */
    public Set<Addon> getAddons() {
        return addons;
    }
}