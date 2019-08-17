package me.piggypiglet.framework.registerables.startup.commands;

import com.google.inject.Inject;
import me.piggypiglet.framework.commands.Command;
import me.piggypiglet.framework.commands.CommandHandlers;
import me.piggypiglet.framework.reflection.Reflections;
import me.piggypiglet.framework.registerables.StartupRegisterable;

import java.util.stream.Collectors;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class CommandsRegisterable extends StartupRegisterable {
    @Inject private Reflections reflections;
    @Inject private CommandHandlers commandHandlers;

    @Override
    protected void execute() {
        commandHandlers.clearCommands();
        commandHandlers.getCommands().addAll(reflections.getSubTypesOf(Command.class).stream().map(injector::getInstance).collect(Collectors.toList()));
    }
}
