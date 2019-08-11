package me.piggypiglet.framework.console;

import me.piggypiglet.framework.logging.Logger;
import me.piggypiglet.framework.logging.LoggerFactory;
import me.piggypiglet.framework.user.User;

import java.util.ArrayList;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class ConsoleUser extends User {
    private static final Logger LOGGER = LoggerFactory.getLogger("Console");

    public ConsoleUser() {
        super("Console", "", new ArrayList<>());
    }

    @Override
    protected void sendMessage(String message) {
        LOGGER.info(message);
    }
}
