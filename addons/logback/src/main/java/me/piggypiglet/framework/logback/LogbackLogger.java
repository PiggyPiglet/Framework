package me.piggypiglet.framework.logback;

import me.piggypiglet.framework.logging.Logger;
import org.slf4j.LoggerFactory;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class LogbackLogger extends Logger<org.slf4j.Logger> {
    @Override
    protected org.slf4j.Logger init(String name) {
        return LoggerFactory.getLogger(name);
    }

    @Override
    protected void info(String message) {
        logger.info(message);
    }

    @Override
    protected void warning(String message) {
        logger.warn(message);
    }

    @Override
    protected void error(String message) {
        logger.error(message);
    }
}
