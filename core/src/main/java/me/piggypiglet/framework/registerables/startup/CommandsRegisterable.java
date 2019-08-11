package me.piggypiglet.framework.registerables.startup;

import com.google.inject.Inject;
import me.piggypiglet.framework.Framework;
import me.piggypiglet.framework.commands.Command;
import me.piggypiglet.framework.commands.CommandHandlers;
import me.piggypiglet.framework.registerables.StartupRegisterable;
import org.reflections.Reflections;

import java.util.stream.Collectors;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class CommandsRegisterable extends StartupRegisterable {
    @Inject private Reflections reflections;
    @Inject private Framework framework;
    @Inject private CommandHandlers commandHandlers;

    @Override
    protected void execute() {
        commandHandlers.getCommands().addAll(reflections.getSubTypesOf(Command.class).stream().map(injector::getInstance).collect(Collectors.toList()));
        commandHandlers.newHandler("main", injector);
    }
}
