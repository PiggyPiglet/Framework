package me.piggypiglet.framework.sponge.logging;

import com.google.inject.Inject;
import me.piggypiglet.framework.logging.Logger;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class SpongeLogger extends Logger<org.slf4j.Logger> {
    @Inject private org.slf4j.Logger logger;

    @Override
    protected org.slf4j.Logger init(String name) {
        return logger;
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
