package me.piggypiglet.framework.bungeecord.task;

import com.google.inject.Inject;
import me.piggypiglet.framework.task.GRunnable;
import me.piggypiglet.framework.task.Task;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.scheduler.TaskScheduler;
import sh.okx.timeapi.TimeAPI;

import java.util.concurrent.TimeUnit;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class BungeeTask extends Task {
    @Inject private Plugin plugin;

    @Override
    protected void async(GRunnable task) {
        plugin.getProxy().getScheduler().runAsync(plugin, task);
    }

    @Override
    protected void scheduleAsync(GRunnable task, TimeAPI time, boolean repeat) {
        final TaskScheduler scheduler = plugin.getProxy().getScheduler();
        final long millis = time.getMilliseconds();

        if (repeat) {
            scheduler.schedule(plugin, task, millis, millis, TimeUnit.MILLISECONDS);
            return;
        }

        scheduler.schedule(plugin, task, millis, TimeUnit.MILLISECONDS);
    }
}
