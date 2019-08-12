package me.piggypiglet.framework.access;

import com.google.inject.Inject;
import com.google.inject.Injector;
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

    public void newHandler(String name, Injector injector) {
        commandHandlers.newHandler(name, injector);
    }
}
