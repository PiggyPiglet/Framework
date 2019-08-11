package me.piggypiglet.framework.task.implementations;

import me.piggypiglet.framework.task.GRunnable;
import me.piggypiglet.framework.task.Task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class DefaultTask extends Task {
    private static final ExecutorService EXECUTOR = Executors.newFixedThreadPool(15);
    private static final ScheduledExecutorService SCHEDULER = Executors.newScheduledThreadPool(10);

    @Override
    public void async(Consumer<GRunnable> task) {
        EXECUTOR.submit(gRunnable(task));
    }

    @Override
    protected void scheduleAsync(Consumer<GRunnable> task, long millis, boolean repeat) {
        final GRunnable gRunnable = gRunnable(task);

        if (repeat) {
            SCHEDULER.scheduleAtFixedRate(gRunnable, millis, millis, TimeUnit.MILLISECONDS);
        } else {
            SCHEDULER.schedule(gRunnable, millis, TimeUnit.MILLISECONDS);
        }
    }

    @Override
    public void shutdown() {
        EXECUTOR.shutdownNow();
        SCHEDULER.shutdownNow();
    }
}
