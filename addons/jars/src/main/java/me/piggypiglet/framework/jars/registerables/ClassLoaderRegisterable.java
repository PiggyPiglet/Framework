package me.piggypiglet.framework.jars.registerables;

import com.google.inject.Inject;
import me.piggypiglet.framework.guice.objects.MainBinding;
import me.piggypiglet.framework.registerables.StartupRegisterable;

import java.net.URLClassLoader;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class ClassLoaderRegisterable extends StartupRegisterable {
    @Inject private MainBinding main;

    @Override
    protected void execute() {
        addBinding(URLClassLoader.class, main.getInstance().getClass().getClassLoader());
    }
}
