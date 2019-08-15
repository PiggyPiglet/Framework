package me.piggypiglet.framework.commands.implementations;

import com.google.inject.Inject;
import me.piggypiglet.framework.Framework;
import me.piggypiglet.framework.commands.Command;
import me.piggypiglet.framework.commands.CommandHandlers;
import me.piggypiglet.framework.user.User;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class HelpCommand extends Command {
    @Inject private CommandHandlers commandHandlers;
    @Inject private Framework framework;

    protected HelpCommand() {
        super("help");
        options.description("Help command.").usage("");
    }

    @Override
    protected boolean execute(User user, String[] args) {
        StringBuilder message = new StringBuilder();

        commandHandlers.getCommands().forEach(c -> {
                    message.append(framework.getCommandPrefix()).append(c.getCommand());

                    if (!c.getUsage().isEmpty()) {
                        message.append(" ").append(c.getUsage());
                    }

                    message.append(" - ").append(c.getDescription()).append("\n");
                });

        user.sendMessage(message.toString());
        return true;
    }
}
