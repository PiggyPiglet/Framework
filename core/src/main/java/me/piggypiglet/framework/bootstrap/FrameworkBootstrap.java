package me.piggypiglet.framework.bootstrap;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.inject.Injector;
import me.piggypiglet.framework.Framework;
import me.piggypiglet.framework.guice.modules.BindingSetterModule;
import me.piggypiglet.framework.guice.modules.InitialModule;
import me.piggypiglet.framework.logging.LoggerFactory;
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
import me.piggypiglet.framework.utils.annotations.registerable.RegisterableData;

import java.util.Arrays;
import java.util.Collection;
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
    private final AtomicReference<Injector> injector = new AtomicReference<>();
    private final Set<StartupRegisterable> registerables = new LinkedHashSet<>();
    private final Set<Addon> addons = new LinkedHashSet<>();

    private final Framework config;

    public FrameworkBootstrap(Framework config) {
        this.config = config;

        if (config.getInjector() == null) {
            injector.set(new InitialModule(this, config).createInjector());
        } else {
            injector.set(config.getInjector().createChildInjector(new InitialModule(this, config)));
        }

        start();
    }

    private void start() {
        addons.addAll(injector.get().getInstance(Reflections.class).getTypesAnnotatedWith(Addon.class).stream()
                .map(c -> c.getAnnotation(Addon.class))
                .collect(Collectors.toSet()));

        final Multimap<BootPriority, Class<? extends StartupRegisterable>> registerables = ArrayListMultimap.create();

        registerables.putAll(BootPriority.IMPL, linkedHashSet(ImplementationFinderRegisterable.class, FileTypesRegisterable.class, FilesRegisterable.class));
        registerables.putAll(BootPriority.COMMANDS, linkedHashSet(CommandsRegisterable.class, CommandHandlerRegisterable.class));
        registerables.putAll(BootPriority.SHUTDOWN, linkedHashSet(ManagersRegisterable.class, ShutdownRegisterablesRegisterable.class, ShutdownHookRegisterable.class));

        addons.stream()
                .map(Addon::startup)
                .map(Arrays::stream)
                .map(s -> s.map(RegisterableData::new))
                .forEach(s -> processRegisterableData(s, registerables));

        processRegisterableData(config.getStartupRegisterables().stream(), registerables);

        for (BootPriority priority : BootPriority.values()) {
            final Collection<Class<? extends StartupRegisterable>> section = registerables.get(priority);

            section.forEach(r -> {
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

        LoggerFactory.getLogger("RPF").info("Bootstrap process completed.");
    }

    @SafeVarargs
    private final Set<Class<? extends StartupRegisterable>> linkedHashSet(Class<? extends StartupRegisterable>... registerables) {
        return Arrays.stream(registerables).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private void processRegisterableData(Stream<RegisterableData> data, Multimap<BootPriority, Class<? extends StartupRegisterable>> registerables) {
        data.forEach(r -> registerables.put(r.getPriority(), r.getRegisterable()));
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