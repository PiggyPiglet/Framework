package me.piggypiglet.framework.sponge.registerables;

import me.piggypiglet.framework.logging.Logger;
import me.piggypiglet.framework.logging.LoggerFactory;
import me.piggypiglet.framework.registerables.StartupRegisterable;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class LoggerRegisterable extends StartupRegisterable {
    @Override
    protected void execute() {
        addBinding(Logger.class, LoggerFactory.getLogger(""));
    }
}
