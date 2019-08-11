package me.piggypiglet.framework.logging.implementations;

import me.piggypiglet.framework.logging.Logger;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class DefaultLogger extends Logger<java.util.logging.Logger> {
    public DefaultLogger(String name) {
        super(java.util.logging.Logger.getLogger(name));
    }

    @Override
    protected void info(String message) {
        logger.info(message);
    }

    @Override
    protected void warning(String message) {
        logger.warning(message);
    }

    @Override
    protected void error(String message) {
        logger.severe(message);
    }
}
