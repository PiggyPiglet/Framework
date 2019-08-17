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

    /**
     * Find and run a command in a specific handler.
     * @param commandHandler Handler to attempt to find the command in
     * @param user User running the command
     * @param message Message, including command prefix, command, and arguments.
     */
    public void processCommand(String commandHandler, User user, String message) {
        commandHandlers.process(commandHandler, user, message);
    }

    /**
     * Create a new command handler.
     * @param name String the command handler will be referenced by
     * @param injector Instance of the applications injector.
     */
    public void newHandler(String name, Injector injector) {
        commandHandlers.newHandler(name, injector);
    }
}
