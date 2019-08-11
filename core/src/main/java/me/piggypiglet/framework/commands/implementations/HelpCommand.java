package me.piggypiglet.framework.commands.implementations;

import me.piggypiglet.framework.commands.Command;
import me.piggypiglet.framework.user.User;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class HelpCommand extends Command {
    protected HelpCommand() {
        super("help");
    }

    @Override
    protected boolean execute(User user, String[] args) {
        user.sendMessage("test");
        return true;
    }
}
