package me.piggypiglet.framework.jars.registerables;

import com.google.inject.Inject;
import me.piggypiglet.framework.jars.loading.JarLoader;
import me.piggypiglet.framework.jars.loading.Loader;
import me.piggypiglet.framework.reflection.Reflections;
import me.piggypiglet.framework.registerables.StartupRegisterable;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class LoaderRegisterable extends StartupRegisterable {
    @Inject private Reflections reflections;
    @Inject private JarLoader jarLoader;

    @Override
    protected void execute() {
        reflections.getSubTypesOf(Loader.class).stream().map(injector::getInstance).forEach(l -> jarLoader.getLoaders().put(((Loader) l).getJarType(), l));
    }
}
