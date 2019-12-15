/*
 * MIT License
 *
 * Copyright (c) 2019 PiggyPiglet
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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
