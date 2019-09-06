package me.piggypiglet.framework.velocity.task;

import com.google.inject.Inject;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.scheduler.Scheduler;
import me.piggypiglet.framework.guice.objects.MainBinding;
import me.piggypiglet.framework.task.GRunnable;
import me.piggypiglet.framework.task.Task;
import sh.okx.timeapi.TimeAPI;

import java.util.concurrent.TimeUnit;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class VelocityTask extends Task {
    @Inject private ProxyServer proxyServer;
    @Inject private MainBinding main;

    @Override
    protected void async(GRunnable task) {
        proxyServer.getScheduler().buildTask(main.getInstance(), task).schedule();
    }

    @Override
    protected void scheduleAsync(GRunnable task, TimeAPI time, boolean repeat) {
        Scheduler.TaskBuilder builder = proxyServer.getScheduler().buildTask(main.getInstance(), task).delay(time.getMilliseconds(), TimeUnit.MILLISECONDS);

        if (repeat) {
            builder.repeat(time.getMilliseconds(), TimeUnit.MILLISECONDS);
        }

        builder.schedule();
    }
}
