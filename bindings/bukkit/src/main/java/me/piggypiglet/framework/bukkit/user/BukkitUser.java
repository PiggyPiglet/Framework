package me.piggypiglet.framework.bukkit.user;

import me.piggypiglet.framework.user.User;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;

import java.util.stream.Collectors;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class BukkitUser extends User {
    private final CommandSender sender;

    public BukkitUser(CommandSender sender) {
        super(
                sender.getName(),
                sender instanceof Player ? ((Player) sender).getUniqueId().toString() : "console",
                sender.getEffectivePermissions().stream().map(PermissionAttachmentInfo::getPermission).collect(Collectors.toList())
        );

        this.sender = sender;
    }

    @Override
    protected void sendMessage(String message) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    public boolean isPlayer() {
        return sender instanceof Player;
    }

    public ConsoleCommandSender getAsConsole() {
        return (ConsoleCommandSender) sender;
    }

    public Player getAsPlayer() {
        return (Player) sender;
    }
}
