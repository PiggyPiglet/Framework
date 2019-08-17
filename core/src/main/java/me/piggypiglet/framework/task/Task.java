package me.piggypiglet.framework.task;

import com.google.inject.ImplementedBy;
import me.piggypiglet.framework.task.implementations.DefaultTask;
import sh.okx.timeapi.TimeAPI;

import java.util.function.Consumer;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
@ImplementedBy(DefaultTask.class)
public abstract class Task {
    /**
     * Implementation of running a runnable async
     * @param task Runnable to run
     */
    protected abstract void async(GRunnable task);

    /**
     * Run a task async
     * @param task Task to run
     */
    public void async(Consumer<GRunnable> task) {
        async(gRunnable(task));
    }

    /**
     * Implementation of scheduling an async runnable
     * @param task Task to run
     * @param time Time till it runs (or interval)
     * @param repeat Whether the task should be on loop, or only run once.
     */
    protected abstract void scheduleAsync(GRunnable task, TimeAPI time, boolean repeat);

    /**
     * Schedule a task to run after a specific amount of time, or every period of time.
     * @param task Task to run
     * @param time Interval or initial delay the task will run at, uses human readable formatting. Available keys are as follows:
     *             <ul>
     *               <li>Seconds:- s, sec, secs, second, seconds</li>
     *               <li>Minutes:- m, min, mins, minute, minutes</li>
     *               <li>Hours:- h, hr, hrs, hour, hours</li>
     *               <li>Days:- d, dy, dys, day, days</li>
     *               <li>Weeks:- w, week, weeks</li>
     *               <li>Months:- mo, mon, mnth, month, months</li>
     *               <li>Years:- y, yr, yrs, year, years</li>
     *             </ul>
     *             The following are examples of use:
     *             <ul>
     *               <li>"10 secs 5 mins" - 10 seconds & 5 minutes</li>
     *               <li>"20m4w5hrs" - 20 minutes, 4 weeks & 5 hours</li>
     *             </ul>
     * @param repeat Whether to loop
     */
    public void async(Consumer<GRunnable> task, String time, boolean repeat) {
        scheduleAsync(gRunnable(task), new TimeAPI(time), repeat);
    }

    /**
     * Shutdown anything that needs to be shutdown
     */
    public void shutdown() {}

    private GRunnable gRunnable(Consumer<GRunnable> consumer) {
        return new GRunnable() {
            @Override
            public void run() {
                consumer.accept(this);
            }
        };
    }
}
