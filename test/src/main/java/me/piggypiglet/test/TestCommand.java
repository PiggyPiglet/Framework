package me.piggypiglet.test;

import com.google.inject.Inject;
import me.piggypiglet.framework.commands.Command;
import me.piggypiglet.framework.file.framework.FileConfiguration;
import me.piggypiglet.framework.user.User;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class TestCommand extends Command {
    @Inject @Config private FileConfiguration config;

    public TestCommand() {
        super("test");
    }

    @Override
    protected boolean execute(User user, String[] args) {
        user.sendMessage(config.getString("test"));
        return true;
    }
}
