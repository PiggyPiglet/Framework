package me.piggypiglet.framework.velocity.commands;

import com.google.inject.Inject;
import com.velocitypowered.api.command.Command;
import com.velocitypowered.api.command.CommandSource;
import me.piggypiglet.framework.Framework;
import me.piggypiglet.framework.access.AccessManager;
import me.piggypiglet.framework.velocity.user.VelocityUser;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class VelocityCommandExecutor implements Command {
    @Inject private AccessManager accessManager;
    @Inject private Framework framework;

    @Override
    public void execute(CommandSource source, String[] args) {
        accessManager.processCommand("velocity", new VelocityUser(source), framework.getCommandPrefix() + " " + String.join(" ", args));
    }
}
