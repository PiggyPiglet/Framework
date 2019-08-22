package me.piggypiglet.framework.jda.user;

import me.piggypiglet.framework.user.User;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.List;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class JDAUser extends User {
    private final TextChannel channel;

    public JDAUser(net.dv8tion.jda.api.entities.User user, TextChannel channel, List<String> permissions) {
        super(
                user.getName(),
                user.getId(),
                permissions
        );
        this.channel = channel;
    }

    @Override
    protected void sendMessage(String message) {
        channel.sendMessage(message).queue();
    }
}
