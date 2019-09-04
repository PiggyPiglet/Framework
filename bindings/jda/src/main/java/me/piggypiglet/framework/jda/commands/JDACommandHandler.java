package me.piggypiglet.framework.jda.commands;

import com.google.inject.Inject;
import me.piggypiglet.framework.access.AccessManager;
import me.piggypiglet.framework.jda.user.JDAUser;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class JDACommandHandler extends ListenerAdapter {
    @Inject private AccessManager accessManager;

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent e) {
        if (!e.getAuthor().isBot()) {
            accessManager.processCommand("main", new JDAUser(e.getAuthor(), e.getChannel()), e.getMessage().getContentRaw());
        }
    }
}
