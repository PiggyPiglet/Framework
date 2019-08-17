package me.piggypiglet.framework.mysql.registerables.startup;

import com.google.inject.Inject;
import me.piggypiglet.framework.file.framework.FileConfiguration;
import me.piggypiglet.framework.managers.ManagersManager;
import me.piggypiglet.framework.mysql.annotations.SQLConfig;
import me.piggypiglet.framework.mysql.manager.MySQLManager;
import me.piggypiglet.framework.registerables.StartupRegisterable;
import me.piggypiglet.framework.task.Task;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class MySQLSavingRegisterable extends StartupRegisterable {
    @Inject private Task task;
    @Inject @SQLConfig private FileConfiguration config;
    @Inject private ManagersManager managersManager;

    @SuppressWarnings("unchecked")
    @Override
    protected void execute() {
        task.async(r -> managersManager.getManagers()
                        .stream()
                        .filter(m -> m instanceof MySQLManager).map(m -> (MySQLManager) m)
                        .forEach(m -> m.getAll().forEach(m.getTable()::save)),
                config.getString("save-interval"), true);
    }
}
