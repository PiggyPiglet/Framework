package me.piggypiglet.framework.velocity.task;

import com.google.inject.Inject;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.scheduler.Scheduler;
import me.piggypiglet.framework.task.GRunnable;
import me.piggypiglet.framework.task.Task;
import me.piggypiglet.framework.utils.annotations.Main;
import sh.okx.timeapi.TimeAPI;

import java.util.concurrent.TimeUnit;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class VelocityTask extends Task {
    @Inject private ProxyServer proxyServer;
    @Inject @Main private Object main;

    @Override
    protected void async(GRunnable task) {
        proxyServer.getScheduler().buildTask(main, task).schedule();
    }

    @Override
    protected void scheduleAsync(GRunnable task, TimeAPI time, boolean repeat) {
        Scheduler.TaskBuilder builder = proxyServer.getScheduler().buildTask(main, task).delay(time.getMilliseconds(), TimeUnit.MILLISECONDS);

        if (repeat) {
            builder.repeat(time.getMilliseconds(), TimeUnit.MILLISECONDS);
        }

        builder.schedule();
    }
}
