package me.piggypiglet.framework.task.implementations;

import me.piggypiglet.framework.task.GRunnable;
import me.piggypiglet.framework.task.Task;
import sh.okx.timeapi.TimeAPI;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class DefaultTask extends Task {
    private static final ExecutorService EXECUTOR = Executors.newFixedThreadPool(15);
    private static final ScheduledExecutorService SCHEDULER = Executors.newScheduledThreadPool(10);

    @Override
    protected void async(GRunnable task) {
        EXECUTOR.submit(task);
    }

    @Override
    protected void scheduleAsync(GRunnable task, TimeAPI time, boolean repeat) {
        long millis = time.getMilliseconds();

        if (repeat) {
            SCHEDULER.scheduleAtFixedRate(task, millis, millis, TimeUnit.MILLISECONDS);
        } else {
            SCHEDULER.schedule(task, millis, TimeUnit.MILLISECONDS);
        }
    }

    @Override
    public void shutdown() {
        EXECUTOR.shutdownNow();
        SCHEDULER.shutdownNow();
    }
}
