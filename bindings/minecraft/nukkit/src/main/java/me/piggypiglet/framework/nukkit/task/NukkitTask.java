package me.piggypiglet.framework.nukkit.task;

import cn.nukkit.Server;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.scheduler.AsyncTask;
import cn.nukkit.scheduler.ServerScheduler;
import com.google.inject.Inject;
import me.piggypiglet.framework.guice.objects.MainBinding;
import me.piggypiglet.framework.task.GRunnable;
import me.piggypiglet.framework.task.Task;
import sh.okx.timeapi.TimeAPI;

public final class NukkitTask extends Task {
    private static final ServerScheduler SCHEDULER = Server.getInstance().getScheduler();

    private final Plugin main;

    @Inject
    public NukkitTask(MainBinding main) {
        this.main = (Plugin) main.getInstance();
    }

    @Override
    protected void async(GRunnable task) {
        SCHEDULER.scheduleAsyncTask(main, task(task));
    }

    @Override
    protected void scheduleAsync(GRunnable task, TimeAPI time, boolean repeat) {
        final int ticks = (int) time.getTicks();

        if (repeat) {
            SCHEDULER.scheduleDelayedRepeatingTask(main, task, ticks, ticks, true);
        } else {
            SCHEDULER.scheduleDelayedTask(main, task, ticks, true);
        }
    }

    private AsyncTask task(GRunnable runnable) {
        return new AsyncTask() {
            @Override
            public void onRun() {
                runnable.run();
            }
        };
    }
}
