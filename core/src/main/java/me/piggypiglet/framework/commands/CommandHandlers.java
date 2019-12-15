/*
 * MIT License
 *
 * Copyright (c) 2019 PiggyPiglet
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package me.piggypiglet.framework.commands;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.piggypiglet.framework.commands.framework.Command;
import me.piggypiglet.framework.guice.objects.Injector;
import me.piggypiglet.framework.task.Task;
import me.piggypiglet.framework.user.User;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Singleton
public final class CommandHandlers {

    @Inject private Task task;

    private final Set<Command> commands = new HashSet<>();
    private final Map<String, CommandHandler> handlers = new HashMap<>();

    /**
     * Find and run a command in a specific handler.
     * @param commandHandler Handler to attempt to find the command in
     * @param user User running the command
     * @param message Message, including command prefix, command, and arguments.
     */
    public void process(String commandHandler, User user, String message) {
        task.async(r -> handlers.get(commandHandler).handle(user, message, commandHandler));
    }

    /**
     * Create a new command handler.
     * @param name String the command handler will be referenced by
     * @param injector Instance of the applications injector.
     */
    public void newHandler(String name, Injector injector) {
        newHandler(name, CommandHandler.class, injector);
    }

    /**
     * Create a new command handler, with a custom type
     * @param name String the command handler will be referenced by
     * @param handler Class of the command handler
     * @param injector Instance of the application's injector
     */
    public void newHandler(String name, Class<? extends CommandHandler> handler, Injector injector) {
        final CommandHandler commandHandler = injector.getInstance(handler);

        handlers.put(name, commandHandler);
        commandHandler.setCommands(commands.stream().filter(c -> c.getHandlers().isEmpty() || c.getHandlers().contains(name)).collect(Collectors.toSet()));
    }

    public void overrideHandler(String name, Class<? extends CommandHandler> handler, Injector injector) {
        if (handlers.containsKey(name)) {
            CommandHandler commandHandler = injector.getInstance(handler);

            commandHandler.setCommands(handlers.get(name).getCommands());
            handlers.put(name, commandHandler);
        }
    }

    /**
     * Get the commands from a specific handler
     * @param handler Handler name
     * @return Set of commands that handler can process
     */
    public Set<Command> getCommands(String handler) {
        return handlers.get(handler).getCommands();
    }

    /**
     * Get all commands registered in RPF
     * @return List of commands
     */
    public Set<Command> getCommands() {
        return commands;
    }

    /**
     * Clear commands in here, and all underlying command handlers.
     */
    public void clearCommands() {
        commands.clear();
        handlers.values().stream().map(CommandHandler::getCommands).forEach(Set::clear);
    }
}
