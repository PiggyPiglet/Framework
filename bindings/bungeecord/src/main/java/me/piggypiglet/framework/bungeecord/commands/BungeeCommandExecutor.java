package me.piggypiglet.framework.bungeecord.commands;

import com.google.inject.Inject;
import me.piggypiglet.framework.Framework;
import me.piggypiglet.framework.access.AccessManager;
import me.piggypiglet.framework.bungeecord.user.BungeeUser;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class BungeeCommandExecutor {
    @Inject private AccessManager accessManager;
    @Inject private Framework framework;

    public Command getExecutor() {
        return new Executor();
    }

    public final class Executor extends Command {
        private Executor() {
            super(framework.getCommandPrefix());
        }

        @Override
        public void execute(CommandSender sender, String[] args) {
            accessManager.processCommand("bungee", new BungeeUser(sender), framework.getCommandPrefix() + " " + String.join(" ", args));
        }
    }
}
