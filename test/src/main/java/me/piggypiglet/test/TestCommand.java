package me.piggypiglet.test;

import com.google.inject.Inject;
import me.piggypiglet.framework.commands.Command;
import me.piggypiglet.framework.user.User;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class TestCommand extends Command {
    @Inject @Oof private String test;

    public TestCommand() {
        super("test");
    }

    @Override
    protected boolean execute(User user, String[] args) {
        user.sendMessage("oof");
        return true;
    }
}
