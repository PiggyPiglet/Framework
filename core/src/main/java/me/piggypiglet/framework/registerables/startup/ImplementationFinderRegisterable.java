package me.piggypiglet.framework.registerables.startup;

import com.google.inject.Inject;
import me.piggypiglet.framework.registerables.StartupRegisterable;
import me.piggypiglet.framework.task.Task;
import org.reflections.Reflections;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class ImplementationFinderRegisterable extends StartupRegisterable {
    @Inject private Reflections reflections;

    @Override
    protected void execute() {
        reflections.getSubTypesOf(Task.class).stream().findFirst().ifPresent(t -> addBinding(Task.class, injector.getInstance(t)));
    }
}
