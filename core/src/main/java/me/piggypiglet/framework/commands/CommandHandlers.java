package me.piggypiglet.framework.commands;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import me.piggypiglet.framework.task.Task;
import me.piggypiglet.framework.user.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
@Singleton
public final class CommandHandlers {
    @Inject private Task task;

    private final List<Command> commands = new ArrayList<>();
    private final Map<String, CommandHandler> commandHandlers = new HashMap<>();

    /**
     * Find and run a command in a specific handler.
     * @param commandHandler Handler to attempt to find the command in
     * @param user User running the command
     * @param message Message, including command prefix, command, and arguments.
     */
    public void process(String commandHandler, User user, String message) {
        task.async(r -> commandHandlers.get(commandHandler).process(user, message));
    }

    /**
     * Create a new command handler.
     * @param name String the command handler will be referenced by
     * @param injector Instance of the applications injector.
     */
    public void newHandler(String name, Injector injector) {
        CommandHandler commandHandler = injector.getInstance(CommandHandler.class);

        commandHandlers.put(name, commandHandler);
        commandHandler.setCommands(commands.stream().filter(c -> c.getHandlers().isEmpty() || c.getHandlers().contains(name)).collect(Collectors.toList()));
    }

    /**
     * Get the commands from a specific handler
     * @param handler Handler name
     * @return List of commands that handler can process
     */
    public List<Command> getCommands(String handler) {
        return commandHandlers.get(handler).getCommands();
    }

    /**
     * Get all commands registered in RPF
     * @return List of commands
     */
    public List<Command> getCommands() {
        return commands;
    }

    /**
     * Clear commands in here, and all underlying command handlers.
     */
    public void clearCommands() {
        commands.clear();
        commandHandlers.values().stream().map(CommandHandler::getCommands).forEach(List::clear);
    }
}
