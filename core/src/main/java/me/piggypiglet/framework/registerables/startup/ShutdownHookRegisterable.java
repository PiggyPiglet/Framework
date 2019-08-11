package me.piggypiglet.framework.registerables.startup;

import com.google.inject.Inject;
import me.piggypiglet.framework.hooks.ShutdownHook;
import me.piggypiglet.framework.registerables.StartupRegisterable;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class ShutdownHookRegisterable extends StartupRegisterable {
    @Inject private ShutdownHook shutdownHook;

    @Override
    protected void execute() {
        Runtime.getRuntime().addShutdownHook(shutdownHook);
    }
}
