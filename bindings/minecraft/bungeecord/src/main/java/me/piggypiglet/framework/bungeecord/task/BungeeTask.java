/*
 * MIT License
 *
 * Copyright (c) 2019-2020 PiggyPiglet
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

package me.piggypiglet.framework.bungeecord.task;

import com.google.inject.Inject;
import me.piggypiglet.framework.guice.objects.MainBinding;
import me.piggypiglet.framework.task.GRunnable;
import me.piggypiglet.framework.task.Task;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.scheduler.TaskScheduler;
import sh.okx.timeapi.TimeAPI;

import java.util.concurrent.TimeUnit;

public final class BungeeTask extends Task {
    private final Plugin plugin;
    private final TaskScheduler scheduler;

    @Inject
    public BungeeTask(MainBinding binding) {
        this.plugin = (Plugin) binding.getInstance();
        this.scheduler = plugin.getProxy().getScheduler();
    }

    @Override
    protected void async(GRunnable task) {
        scheduler.runAsync(plugin, task);
    }

    @Override
    protected void scheduleAsync(GRunnable task, TimeAPI time, boolean repeat) {
        final long millis = time.getMilliseconds();

        if (repeat) {
            scheduler.schedule(plugin, task, millis, millis, TimeUnit.MILLISECONDS);
            return;
        }

        scheduler.schedule(plugin, task, millis, TimeUnit.MILLISECONDS);
    }

    @Override
    protected void sync(GRunnable task) {
        async(task);
    }
}
