package me.piggypiglet.framework.registerables.startup.commands;

import com.google.inject.Inject;
import me.piggypiglet.framework.commands.CommandHandlers;
import me.piggypiglet.framework.registerables.StartupRegisterable;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class CommandHandlerRegisterable extends StartupRegisterable {
    @Inject private CommandHandlers commandHandlers;

    @Override
    protected void execute() {
        commandHandlers.newHandler("main", injector);
    }
}
