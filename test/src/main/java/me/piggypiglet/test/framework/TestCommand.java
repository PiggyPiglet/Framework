package me.piggypiglet.test.framework;

import me.piggypiglet.framework.commands.Command;
import me.piggypiglet.framework.user.User;
import me.piggypiglet.test.Test;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class TestCommand extends Command {
    public TestCommand() {
        super("test");
    }

    @Override
    protected boolean execute(User user, String[] args) {
        try {
            new Test().start();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        user.sendMessage("doesn't exist :(");
        return true;
    }
}
