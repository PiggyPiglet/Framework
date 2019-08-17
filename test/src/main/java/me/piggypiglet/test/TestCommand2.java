package me.piggypiglet.test;

import me.piggypiglet.framework.commands.Command;
import me.piggypiglet.framework.user.User;
import me.piggypiglet.framework.utils.annotations.reflection.Disabled;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
@Disabled
public final class TestCommand2 extends Command {
    public TestCommand2() {
        super("test2");
    }

    @Override
    protected boolean execute(User user, String[] args) {
        return true;
    }
}
