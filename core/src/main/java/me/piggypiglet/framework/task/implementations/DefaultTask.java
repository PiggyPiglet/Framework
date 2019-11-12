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

package me.piggypiglet.framework.task.implementations;

import me.piggypiglet.framework.task.GRunnable;
import me.piggypiglet.framework.task.Task;
import me.piggypiglet.framework.utils.annotations.reflection.Disabled;
import sh.okx.timeapi.TimeAPI;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Default task manager for RPF. Uses a Fixed Thread Pool and Scheduled Thread Pool to provision async and scheduled async tasks.
 */
@Disabled
public final class DefaultTask extends Task {
    private final ExecutorService executor;
    private final ScheduledExecutorService scheduler;

    public DefaultTask(int threads) {
        this.executor = Executors.newFixedThreadPool(threads);
        this.scheduler = Executors.newScheduledThreadPool(threads);
    }

    @Override
    protected void async(GRunnable task) {
        executor.submit(task);
    }

    @Override
    protected void scheduleAsync(GRunnable task, TimeAPI time, boolean repeat) {
        long millis = time.getMilliseconds();

        if (repeat) {
            scheduler.scheduleAtFixedRate(task, millis, millis, TimeUnit.MILLISECONDS);
        } else {
            scheduler.schedule(task, millis, TimeUnit.MILLISECONDS);
        }
    }

    @Override
    public void shutdown() {
        executor.shutdownNow();
        scheduler.shutdownNow();
    }
}
