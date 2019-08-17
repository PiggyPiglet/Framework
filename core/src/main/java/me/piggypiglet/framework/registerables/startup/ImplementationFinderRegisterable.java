package me.piggypiglet.framework.registerables.startup;

import com.google.inject.Inject;
import com.google.inject.name.Names;
import me.piggypiglet.framework.logging.Logger;
import me.piggypiglet.framework.logging.LoggerFactory;
import me.piggypiglet.framework.logging.implementations.DefaultLogger;
import me.piggypiglet.framework.reflection.Reflections;
import me.piggypiglet.framework.registerables.StartupRegisterable;
import me.piggypiglet.framework.task.Task;
import me.piggypiglet.framework.task.implementations.DefaultTask;

import java.util.Optional;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class ImplementationFinderRegisterable extends StartupRegisterable {
    @Inject private Reflections reflections;

    @Override
    protected void execute() {
        find(Task.class, DefaultTask.class).map(injector::getInstance).ifPresent(t -> addBinding(Task.class, t));
        addAnnotatedBinding(Class.class, Names.named("logger"), find(Logger.class, DefaultLogger.class).orElse(DefaultLogger.class));
        requestStaticInjections(LoggerFactory.class);
    }

    private <T> Optional<Class<? extends T>> find(Class<T> interfaze, Class<? extends T> defaultClass) {
        return reflections.getSubTypesOf(interfaze).stream().filter(c -> c != defaultClass).findFirst();
    }
}
