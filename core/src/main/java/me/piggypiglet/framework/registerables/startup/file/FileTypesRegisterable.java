package me.piggypiglet.framework.registerables.startup.file;

import com.google.inject.Inject;
import me.piggypiglet.framework.file.FileConfigurationFactory;
import me.piggypiglet.framework.file.framework.AbstractFileConfiguration;
import me.piggypiglet.framework.file.implementations.BlankFileConfiguration;
import me.piggypiglet.framework.reflection.Reflections;
import me.piggypiglet.framework.registerables.StartupRegisterable;

import java.util.Set;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class FileTypesRegisterable extends StartupRegisterable {
    @Inject private Reflections reflections;
    @Inject private FileConfigurationFactory fileConfigurationFactory;

    @Override
    protected void execute() {
        Set<Class<? extends AbstractFileConfiguration>> files = reflections.getSubTypesOf(AbstractFileConfiguration.class);
        files.stream().filter(c -> c != BlankFileConfiguration.class).map(injector::getInstance).forEach(f -> fileConfigurationFactory.getConfigTypes().put(f.getMatch(), f.getClass()));
    }
}
