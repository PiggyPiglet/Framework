package me.piggypiglet.framework.registerables.startup.file;

import com.google.inject.Inject;
import me.piggypiglet.framework.Framework;
import me.piggypiglet.framework.file.FileManager;
import me.piggypiglet.framework.file.framework.FileConfiguration;
import me.piggypiglet.framework.file.objects.FileWrapper;
import me.piggypiglet.framework.registerables.StartupRegisterable;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class FilesRegisterable extends StartupRegisterable {
    @Inject private Framework framework;
    @Inject private FileManager fileManager;

    @Override
    protected void execute() {
        framework.getFiles().forEach(f -> {
            try {
                addAnnotatedBinding(
                        f.isConfig() ? FileConfiguration.class : FileWrapper.class,
                        f.getAnnotation(),
                        f.isConfig() ? fileManager.loadConfig(f.getName(), f.getInternalPath(), f.getExternalPath()) : fileManager.loadFile(f.getName(), f.getInternalPath(), f.getExternalPath())
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
