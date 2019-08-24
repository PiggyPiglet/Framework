package me.piggypiglet.framework.bungeecord.logging;

import com.google.inject.Inject;
import me.piggypiglet.framework.logging.Logger;
import net.md_5.bungee.api.plugin.Plugin;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class BungeeLogger extends Logger<java.util.logging.Logger> {
    @Inject private Plugin main;

    @Override
    protected java.util.logging.Logger init(String name) {
        return main.getLogger();
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
