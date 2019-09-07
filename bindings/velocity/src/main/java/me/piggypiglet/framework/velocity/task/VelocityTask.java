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

package me.piggypiglet.framework.velocity.task;

import com.google.inject.Inject;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.scheduler.Scheduler;
import me.piggypiglet.framework.guice.objects.MainBinding;
import me.piggypiglet.framework.task.GRunnable;
import me.piggypiglet.framework.task.Task;
import sh.okx.timeapi.TimeAPI;

import java.util.concurrent.TimeUnit;

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
