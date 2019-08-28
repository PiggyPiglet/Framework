package me.piggypiglet.demo.registerables;

import com.google.inject.Inject;
import me.piggypiglet.framework.file.framework.FileConfiguration;
import me.piggypiglet.framework.registerables.StartupRegisterable;
import me.piggypiglet.framework.task.Task;
import me.piggypiglet.framework.utils.annotations.files.Config;
import org.bukkit.Bukkit;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class BroadcasterRegisterable extends StartupRegisterable {
    @Inject private Task task;
    @Inject @Config private FileConfiguration config;

    @Override
    protected void execute() {
        task.async(r -> Bukkit.broadcastMessage(config.getString("broadcast.message", "Default broadcast message.")), config.getString("broadcast.interval"), true);
    }
}
