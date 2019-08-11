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
     public abstract void async(Consumer<GRunnable> task);

     protected abstract void scheduleAsync(Consumer<GRunnable> task, long millis, boolean repeat);

     public void async(Consumer<GRunnable> task, String time, boolean repeat) {
         scheduleAsync(task, new TimeAPI(time).getMilliseconds(), repeat);
     }

     public void shutdown() {

     }

     protected GRunnable gRunnable(Consumer<GRunnable> consumer) {
         return new GRunnable() {
             @Override
             public void run() {
                 consumer.accept(this);
             }
         };
     }
}
