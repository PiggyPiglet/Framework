package me.piggypiglet.framework.access;

import com.google.inject.Inject;
import me.piggypiglet.framework.commands.CommandHandlers;
import me.piggypiglet.framework.user.User;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class AccessManager {
    @Inject private CommandHandlers commandHandlers;

    public void processCommand(String commandHandler, User user, String message) {
        commandHandlers.process(commandHandler, user, message);
    }
}
