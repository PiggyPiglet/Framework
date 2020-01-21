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

package me.piggypiglet.framework.task;

import sh.okx.timeapi.TimeAPI;

import java.util.function.Consumer;

public abstract class Task {
    /**
     * Implementation of running a runnable async
     *
     * @param task Runnable to run
     */
    protected abstract void async(GRunnable task);

    /**
     * Run a task async
     *
     * @param task Task to run
     */
    public void async(Consumer<GRunnable> task) {
        async(gRunnable(task));
    }

    /**
     * Implementation of scheduling an async runnable
     *
     * @param task   Task to run
     * @param time   Time till it runs (or interval)
     * @param repeat Whether the task should be on loop, or only run once.
     */
    protected abstract void scheduleAsync(GRunnable task, TimeAPI time, boolean repeat);

    /**
     * Schedule a task to run after a specific amount of time, or every period of time.
     *
     * @param task   Task to run
     * @param time   Interval or initial delay the task will run at, uses human readable formatting. Available keys are as follows:
     *               <ul>
     *                 <li>Seconds:- s, sec, secs, second, seconds</li>
     *                 <li>Minutes:- m, min, mins, minute, minutes</li>
     *                 <li>Hours:- h, hr, hrs, hour, hours</li>
     *                 <li>Days:- d, dy, dys, day, days</li>
     *                 <li>Weeks:- w, week, weeks</li>
     *                 <li>Months:- mo, mon, mnth, month, months</li>
     *                 <li>Years:- y, yr, yrs, year, years</li>
     *               </ul>
     *               The following are examples of use:
     *               <ul>
     *                 <li>"10 secs 5 mins" - 10 seconds &amp; 5 minutes</li>
     *                 <li>"20m4w5hrs" - 20 minutes, 4 weeks &amp; 5 hours</li>
     *               </ul>
     * @param repeat Whether to loop
     */
    public void async(Consumer<GRunnable> task, String time, boolean repeat) {
        scheduleAsync(gRunnable(task), new TimeAPI(time), repeat);
    }

    /**
     * Implementation for sync tasks
     *
     * @param task Task
     */
    protected abstract void sync(GRunnable task);

    /**
     * Schedule a sync task.
     *
     * @param task Task
     */
    public void sync(Consumer<GRunnable> task) {
        sync(gRunnable(task));
    }

    /**
     * Shutdown anything that needs to be shutdown
     */
    public void shutdown() {
    }

    private GRunnable gRunnable(Consumer<GRunnable> consumer) {
        return new GRunnable() {
            @Override
            public void run() {
                consumer.accept(this);
            }
        };
    }
}
