package me.piggypiglet.framework.registerables.shutdown;

import com.google.inject.Inject;
import me.piggypiglet.framework.registerables.ShutdownRegisterable;
import me.piggypiglet.framework.task.Task;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class TaskShutdownRegisterable extends ShutdownRegisterable {
    @Inject private Task task;

    @Override
    protected void execute() {
        task.shutdown();
    }
}
