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
     protected abstract void async(GRunnable task);

     public void async(Consumer<GRunnable> task) {
         async(gRunnable(task));
     }

     protected abstract void scheduleAsync(GRunnable task, TimeAPI time, boolean repeat);

     public void async(Consumer<GRunnable> task, String time, boolean repeat) {
         scheduleAsync(gRunnable(task), new TimeAPI(time), repeat);
     }

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
