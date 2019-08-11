package me.piggypiglet.framework.registerables.startup;

import com.google.inject.Inject;
import me.piggypiglet.framework.Framework;
import me.piggypiglet.framework.hooks.ShutdownHook;
import me.piggypiglet.framework.registerables.StartupRegisterable;

import java.util.stream.Collectors;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class ShutdownRegisterablesRegisterable extends StartupRegisterable {
    @Inject private Framework config;
    @Inject private ShutdownHook shutdownHook;

    @Override
    protected void execute() {
        shutdownHook.getRegisterables().addAll(config.getShutdownRegisterables().stream().map(injector::getInstance).collect(Collectors.toList()));
    }
}
