package me.piggypiglet.framework.bungeecord.user;

import me.piggypiglet.framework.user.User;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class BungeeUser extends User {
    private final CommandSender sender;

    public BungeeUser(CommandSender sender) {
        super(
                sender.getName(),
                sender instanceof ProxiedPlayer ? ((ProxiedPlayer) sender).getUniqueId().toString() : "Console",
                new ArrayList<>(sender.getPermissions())
        );

        this.sender = sender;
    }

    @Override
    protected void sendMessage(String message) {
        sender.sendMessage(TextComponent.fromLegacyText(message));
    }
}
