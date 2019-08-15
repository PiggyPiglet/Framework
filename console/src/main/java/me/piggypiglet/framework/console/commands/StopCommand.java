package me.piggypiglet.framework.console.commands;

import me.piggypiglet.framework.commands.Command;
import me.piggypiglet.framework.user.User;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class StopCommand extends Command {
    public StopCommand() {
        super("stop");
        options.handlers("console").usage("").description("Stop the application.");
    }

    @Override
    protected boolean execute(User user, String[] args) {
        System.exit(0);
        return true;
    }
}
