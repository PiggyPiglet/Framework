package me.piggypiglet.framework.velocity.user;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.ConsoleCommandSource;
import com.velocitypowered.api.proxy.Player;
import me.piggypiglet.framework.user.User;
import net.kyori.text.serializer.legacy.LegacyComponentSerializer;

import java.util.ArrayList;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class VelocityUser extends User {
    private final CommandSource source;

    public VelocityUser(CommandSource source) {
        super(
                source instanceof ConsoleCommandSource ? "Console" : ((Player) source).getUsername(),
                source instanceof ConsoleCommandSource ? "Console" : ((Player) source).getUniqueId().toString(),
                new ArrayList<>()
        );

        this.source = source;
    }

    @Override
    protected void sendMessage(String message) {
        source.sendMessage(LegacyComponentSerializer.legacy().deserialize(message));
    }

    @Override
    public boolean hasPermission(String permission) {
        return source.hasPermission(permission);
    }
}
