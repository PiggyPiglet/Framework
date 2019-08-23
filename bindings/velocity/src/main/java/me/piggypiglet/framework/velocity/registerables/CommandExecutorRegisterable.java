package me.piggypiglet.framework.velocity.registerables;

import com.google.inject.Inject;
import com.velocitypowered.api.command.CommandManager;
import me.piggypiglet.framework.Framework;
import me.piggypiglet.framework.registerables.StartupRegisterable;
import me.piggypiglet.framework.velocity.commands.VelocityCommandExecutor;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class CommandExecutorRegisterable extends StartupRegisterable {
    @Inject private CommandManager commandManager;
    @Inject private VelocityCommandExecutor velocityCommandExecutor;
    @Inject private Framework framework;

    @Override
    protected void execute() {
        commandManager.register(velocityCommandExecutor, framework.getCommandPrefix());
    }
}
