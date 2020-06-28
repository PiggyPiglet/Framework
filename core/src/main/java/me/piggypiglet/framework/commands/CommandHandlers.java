/*
 * MIT License
 *
 * Copyright (c) 2019-2020 PiggyPiglet
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

import com.google.common.collect.ImmutableSet;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import me.piggypiglet.framework.Framework;
import me.piggypiglet.framework.commands.framework.Command;
import me.piggypiglet.framework.task.Task;
import me.piggypiglet.framework.user.User;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Singleton
public final class CommandHandlers {
    @Inject private Task task;
    private final boolean enabled;

    @Inject
    public CommandHandlers(@NotNull final Framework config) {
        enabled = config.getCommandPrefixes() != null;
    }

    private final Set<Command<? extends User, ?>> commands = new HashSet<>();
    private final Map<String, CommandHandler> handlers = new HashMap<>();

    public void process(@NotNull final String commandHandler, @NotNull final User user,
                        @NotNull final String message) {
        if (!enabled) return;

        task.async(r -> handlers.get(commandHandler).handle(user, message, commandHandler));
    }

    public void newHandler(@NotNull final String name, @NotNull final Injector injector) {
        if (!enabled) return;

        newHandler(name, CommandHandler.class, injector);
    }

    public void newHandler(@NotNull final String name, @NotNull final Class<? extends CommandHandler> handler,
                           @NotNull final Injector injector) {
        if (!enabled) return;

        final CommandHandler commandHandler = injector.getInstance(handler);

        handlers.put(name, commandHandler);
        updateCommands();
    }

    public void overrideHandler(@NotNull final String name, @NotNull final Class<? extends CommandHandler> handler,
                                @NotNull final Injector injector) {
        if (!enabled || !handlers.containsKey(name)) return;

        final CommandHandler commandHandler = injector.getInstance(handler);

        commandHandler.setCommands(handlers.get(name).getCommands());
        handlers.put(name, commandHandler);
    }

    @NotNull
    public Set<Command<? extends User, ?>> getCommands(@NotNull final String handler) {
        return ImmutableSet.copyOf(handlers.get(handler).getCommands());
    }

    @NotNull
    public Set<Command<? extends User, ?>> getAllCommands() {
        return ImmutableSet.copyOf(commands);
    }

    public void addCommands(@NotNull final Set<Command<? extends User, ?>> commands) {
        if (!enabled) return;

        this.commands.addAll(commands);
        updateCommands();
    }

    public void updateCommands() {
        if (!enabled) return;

        handlers.keySet().forEach(this::updateCommands);
    }

    public void updateCommands(@NotNull final String name) {
        if (!enabled) return;

        handlers.get(name).setCommands(commands.stream()
                .filter(c -> c.getHandlers().isEmpty() || c.getHandlers().contains(name))
                .collect(Collectors.toSet()));
    }

    public void clearCommands() {
        if (!enabled) return;

        commands.clear();
        handlers.values().stream().map(CommandHandler::getCommands).forEach(Set::clear);
    }
}
