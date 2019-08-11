package me.piggypiglet.framework.registerables.startup;

import com.google.inject.Inject;
import me.piggypiglet.framework.commands.Command;
import me.piggypiglet.framework.commands.CommandHandler;
import me.piggypiglet.framework.registerables.StartupRegisterable;
import org.reflections.Reflections;

import java.util.stream.Collectors;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class CommandsRegisterable extends StartupRegisterable {
    @Inject private Reflections reflections;
    @Inject private CommandHandler commandHandler;

    @Override
    protected void execute() {
        commandHandler.setCommands(reflections.getSubTypesOf(Command.class).stream().map(injector::getInstance).collect(Collectors.toList()));
    }
}
