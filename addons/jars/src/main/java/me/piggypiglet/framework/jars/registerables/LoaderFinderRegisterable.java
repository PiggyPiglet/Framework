package me.piggypiglet.framework.jars.registerables;

import com.google.inject.Inject;
import me.piggypiglet.framework.jars.loading.JarManager;
import me.piggypiglet.framework.jars.loading.framework.Loader;
import me.piggypiglet.framework.reflection.Reflections;
import me.piggypiglet.framework.registerables.StartupRegisterable;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class LoaderFinderRegisterable extends StartupRegisterable {
    @Inject private Reflections reflections;
    @Inject private JarManager jarManager;

    @Override
    protected void execute() {
        reflections.getSubTypesOf(Loader.class).stream().map(injector::getInstance).forEach(jarManager::add);
        jarManager.load().scan(injector);
    }
}
