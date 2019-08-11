package me.piggypiglet.framework.registerables.startup;

import com.google.inject.Inject;
import me.piggypiglet.framework.registerables.StartupRegisterable;
import me.piggypiglet.framework.task.Task;
import me.piggypiglet.framework.task.implementations.DefaultTask;
import org.reflections.Reflections;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class ImplementationFinderRegisterable extends StartupRegisterable {
    @Inject private Reflections reflections;

    @Override
    protected void execute() {
        addBindingWithDef(Task.class, DefaultTask.class);
    }

    private <T> void addBindingWithDef(Class interfaze, Class<T> def) {
        addBinding(interfaze, injector.getInstance(reflections.getSubTypesOf(def).stream().findFirst().orElse(def)));
    }
}
