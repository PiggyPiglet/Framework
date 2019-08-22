package me.piggypiglet.framework.mysql.registerables.startup;

import com.google.inject.Inject;
import me.piggypiglet.framework.file.framework.FileConfiguration;
import me.piggypiglet.framework.mysql.annotations.SQLConfig;
import me.piggypiglet.framework.mysql.manager.MySQLManagers;
import me.piggypiglet.framework.registerables.StartupRegisterable;
import me.piggypiglet.framework.task.Task;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class MySQLSavingRegisterable extends StartupRegisterable {
    @Inject private Task task;
    @Inject @SQLConfig private FileConfiguration config;
    @Inject private MySQLManagers mySQLManagers;

    @Override
    protected void execute() {
        task.async(r -> mySQLManagers.saveAll(), config.getString("save-interval"), true);
    }
}
