package me.piggypiglet.test.commands;

import com.google.inject.Inject;
import me.piggypiglet.framework.commands.Command;
import me.piggypiglet.framework.mysql.manager.MySQLManagers;
import me.piggypiglet.framework.task.Task;
import me.piggypiglet.framework.user.User;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class SaveDB extends Command {
    @Inject private Task task;
    @Inject private MySQLManagers mySQLManagers;

    public SaveDB() {
        super("save");
    }

    @Override
    protected boolean execute(User user, String[] args) {
        task.async(r -> mySQLManagers.saveAll());
        return true;
    }
}
