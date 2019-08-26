package me.piggypiglet.framework.registerables.startup.file;

import com.google.inject.Inject;
import me.piggypiglet.framework.Framework;
import me.piggypiglet.framework.bootstrap.FrameworkBootstrap;
import me.piggypiglet.framework.file.FileManager;
import me.piggypiglet.framework.file.framework.FileConfiguration;
import me.piggypiglet.framework.file.objects.FileData;
import me.piggypiglet.framework.file.objects.FileWrapper;
import me.piggypiglet.framework.registerables.StartupRegisterable;
import me.piggypiglet.framework.utils.annotations.addon.Addon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class FilesRegisterable extends StartupRegisterable {
    @Inject private Framework framework;
    @Inject private FileManager fileManager;
    @Inject private FrameworkBootstrap frameworkBootstrap;

    @Override
    protected void execute() {
        final List<FileData> files = new ArrayList<>(framework.getFiles());
        frameworkBootstrap.getAddons().stream()
                .map(Addon::files)
                .map(Arrays::stream)
                .forEach(s -> s.forEach(f -> files.add(new FileData(f.config(), f.name(), f.internalPath(), f.externalPath(), f.annotation()))));

        files.forEach(f -> {
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
