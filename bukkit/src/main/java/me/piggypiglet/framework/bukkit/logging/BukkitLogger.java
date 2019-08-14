package me.piggypiglet.framework.bukkit.logging;

import me.piggypiglet.framework.logging.Logger;
import org.bukkit.Bukkit;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class BukkitLogger extends Logger<java.util.logging.Logger> {
    @Override
    protected java.util.logging.Logger init(String name) {
        return Bukkit.getLogger();
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
