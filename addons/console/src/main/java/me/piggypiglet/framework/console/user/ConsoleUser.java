package me.piggypiglet.framework.console.user;

import me.piggypiglet.framework.logging.Logger;
import me.piggypiglet.framework.logging.LoggerFactory;
import me.piggypiglet.framework.user.User;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class ConsoleUser extends User {
    private static final Logger LOGGER = LoggerFactory.getLogger("Console");

    public ConsoleUser() {
        super("Console", "");
    }

    @Override
    protected void sendMessage(String message) {
        LOGGER.info(message);
    }

    @Override
    public boolean hasPermission(String permission) {
        return true;
    }
}
