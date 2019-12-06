/*
 * MIT License
 *
 * Copyright (c) 2019 PiggyPiglet
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package me.piggypiglet.framework.registerables.startup.file;

import com.google.inject.Inject;
import me.piggypiglet.framework.Framework;
import me.piggypiglet.framework.addon.ConfigManager;
import me.piggypiglet.framework.bootstrap.FrameworkBootstrap;
import me.piggypiglet.framework.file.FileManager;
import me.piggypiglet.framework.file.framework.FileConfiguration;
import me.piggypiglet.framework.file.objects.FileData;
import me.piggypiglet.framework.file.objects.FileWrapper;
import me.piggypiglet.framework.registerables.StartupRegisterable;
import me.piggypiglet.framework.utils.annotations.addon.Addon;
import me.piggypiglet.framework.utils.annotations.addon.File;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class FilesRegisterable extends StartupRegisterable {
    @Inject private Framework framework;
    @Inject private FileManager fileManager;
    @Inject private FrameworkBootstrap frameworkBootstrap;
    @Inject private ConfigManager configManager;

    @Override
    protected void execute() {
        final List<FileData> files = new ArrayList<>(framework.getFiles());

        frameworkBootstrap.getAddons().values().stream()
                .map(Addon::files)
                .map(Arrays::stream)
                .forEach(s -> {
                    for (File f : s.toArray(File[]::new)) {
                        if (f.config() && !configManager.getFilesToBeCreated().contains(f.name())) continue;

                        files.add(new FileData(f.config(), f.name(), f.internalPath(), f.externalPath(), f.annotation()));
                    }
                });

        files.forEach(f -> {
            try {
                final String name = f.getName();
                final String internalPath = f.getInternalPath();
                final String externalPath = f.getExternalPath();
                final Class<? extends Annotation> annotation = f.getAnnotation();

                if (f.isConfig()) {
                    addAnnotatedBinding(FileConfiguration.class, annotation, fileManager.loadConfig(name, internalPath, externalPath));
                } else {
                    addAnnotatedBinding(FileWrapper.class, annotation, fileManager.loadFile(name, internalPath, externalPath));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
