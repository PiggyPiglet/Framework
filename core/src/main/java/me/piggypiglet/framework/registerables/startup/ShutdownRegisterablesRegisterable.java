package me.piggypiglet.framework.registerables.startup;

import com.google.inject.Inject;
import me.piggypiglet.framework.Framework;
import me.piggypiglet.framework.FrameworkBootstrap;
import me.piggypiglet.framework.hooks.ShutdownHook;
import me.piggypiglet.framework.registerables.ShutdownRegisterable;
import me.piggypiglet.framework.registerables.StartupRegisterable;
import me.piggypiglet.framework.registerables.shutdown.TaskShutdownRegisterable;
import me.piggypiglet.framework.utils.annotations.addon.Addon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class ShutdownRegisterablesRegisterable extends StartupRegisterable {
    @Inject private Framework config;
    @Inject private FrameworkBootstrap frameworkBootstrap;
    @Inject private ShutdownHook shutdownHook;

    @Override
    protected void execute() {
        List<Class<? extends ShutdownRegisterable>> shutdown = new ArrayList<>();

        shutdown.add(TaskShutdownRegisterable.class);
        shutdown.addAll(config.getShutdownRegisterables());

        frameworkBootstrap.getAddons().stream()
                .map(Addon::shutdown)
                .map(Arrays::stream)
                .forEach(s -> s.forEach(shutdown::add));

        shutdownHook.getRegisterables().addAll(shutdown.stream().map(injector::getInstance).collect(Collectors.toList()));
    }
}
