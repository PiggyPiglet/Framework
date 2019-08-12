package me.piggypiglet.test;

import com.google.inject.Inject;
import me.piggypiglet.framework.file.framework.FileConfiguration;
import me.piggypiglet.framework.registerables.StartupRegisterable;
import me.piggypiglet.framework.utils.annotations.files.Config;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class TestRegisterable extends StartupRegisterable {
    @Inject @Config private FileConfiguration config;

    @Override
    protected void execute() {
        addAnnotatedBinding(String.class, Oof.class, config.getString("test"));
    }
}
