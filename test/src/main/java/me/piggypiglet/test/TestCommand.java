package me.piggypiglet.test;

import me.piggypiglet.framework.commands.Command;
import me.piggypiglet.framework.user.User;

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
            Class.forName("me.piggypiglet.test.TestClass");
            user.sendMessage("exists");
            return true;
        } catch (Exception ignored) {}

        user.sendMessage("doesn't exist");
        return true;
    }
}
