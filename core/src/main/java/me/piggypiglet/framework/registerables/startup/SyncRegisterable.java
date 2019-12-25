package me.piggypiglet.framework.registerables.startup;

import com.google.inject.Inject;
import me.piggypiglet.framework.logging.LoggerFactory;
import me.piggypiglet.framework.registerables.StartupRegisterable;
import me.piggypiglet.framework.task.Task;
import me.piggypiglet.framework.task.implementations.DefaultTask;

public final class SyncRegisterable extends StartupRegisterable {
    @Inject private Task task;

    @Override
    protected void execute() {
        LoggerFactory.getLogger("RPF").debug("Bootstrap process completed.");

        if (task instanceof DefaultTask) {
            try {
                while (true) ((DefaultTask) task).getQueue().take().run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
