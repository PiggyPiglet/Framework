package me.piggypiglet.framework.bukkit.commands;

import com.google.inject.Inject;
import me.piggypiglet.framework.access.AccessManager;
import me.piggypiglet.framework.bukkit.user.BukkitUser;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import javax.annotation.Nonnull;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class BukkitCommandHandler implements CommandExecutor {
    @Inject private AccessManager accessManager;

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {
        accessManager.processCommand("bukkit", new BukkitUser(sender), label + " " + String.join(" ", args));

        return true;
    }
}
