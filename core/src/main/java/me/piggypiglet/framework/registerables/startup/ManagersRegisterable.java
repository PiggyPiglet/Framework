package me.piggypiglet.framework.registerables.startup;

import com.google.inject.Inject;
import me.piggypiglet.framework.managers.Manager;
import me.piggypiglet.framework.managers.ManagersManager;
import me.piggypiglet.framework.reflection.Reflections;
import me.piggypiglet.framework.registerables.StartupRegisterable;
import me.piggypiglet.framework.task.Task;

import java.lang.reflect.Modifier;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class ManagersRegisterable extends StartupRegisterable {
    @Inject private Task task;
    @Inject private Reflections reflections;
    @Inject private ManagersManager managersManager;

    @Override
    protected void execute() {
        reflections.getSubTypesOf(Manager.class).stream().filter(c -> !Modifier.isAbstract(c.getModifiers())).map(injector::getInstance).forEach(managersManager.getManagers()::add);
        task.async(r -> managersManager.setup());
    }
}
