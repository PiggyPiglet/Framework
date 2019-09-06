package me.piggypiglet.framework.sponge.task;

import com.google.inject.Inject;
import me.piggypiglet.framework.guice.objects.MainBinding;
import me.piggypiglet.framework.task.GRunnable;
import me.piggypiglet.framework.task.Task;
import sh.okx.timeapi.TimeAPI;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class SpongeTaskManager extends Task {
    @Inject private MainBinding main;

    @Override
    protected void async(GRunnable task) {
        builder().async().execute(task).submit(main.getInstance());
    }

    @Override
    protected void scheduleAsync(GRunnable task, TimeAPI time, boolean repeat) {
        org.spongepowered.api.scheduler.Task.Builder builder = builder().async().delayTicks(time.getTicks()).execute(task);

        if (repeat) {
            builder.intervalTicks(time.getTicks());
        }

        builder.submit(main.getInstance());
    }

    private static org.spongepowered.api.scheduler.Task.Builder builder() {
        return org.spongepowered.api.scheduler.Task.builder();
    }
}
