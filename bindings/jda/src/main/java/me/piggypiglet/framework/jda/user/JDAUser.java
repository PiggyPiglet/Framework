package me.piggypiglet.framework.jda.user;

import me.piggypiglet.framework.user.User;
import net.dv8tion.jda.api.entities.TextChannel;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class JDAUser extends User {
    private final TextChannel channel;

    public JDAUser(net.dv8tion.jda.api.entities.User user, TextChannel channel) {
        super(
                user.getName(),
                user.getId()
        );
        this.channel = channel;
    }

    @Override
    protected void sendMessage(String message) {
        channel.sendMessage(message).queue();
    }

    @Override
    public boolean hasPermission(String permission) {
        return true;
    }
}
