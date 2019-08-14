package me.piggypiglet.framework.bukkit.task;

import com.google.inject.Inject;
import me.piggypiglet.framework.task.GRunnable;
import me.piggypiglet.framework.task.Task;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import sh.okx.timeapi.TimeAPI;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class BukkitTask extends Task {
    @Inject private JavaPlugin main;

    @Override
    protected void async(GRunnable task) {
        Bukkit.getScheduler().runTaskAsynchronously(main, task);
    }

    @Override
    protected void scheduleAsync(GRunnable task, TimeAPI time, boolean repeat) {
        Bukkit.getScheduler().runTaskTimerAsynchronously(main, task, time.getTicks(), time.getTicks());
    }
}
