/*
 * MIT License
 *
 * Copyright (c) 2019-2020 PiggyPiglet
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
import me.piggypiglet.framework.file.FileManager;
import me.piggypiglet.framework.file.framework.FileConfiguration;
import me.piggypiglet.framework.file.objects.FileWrapper;
import me.piggypiglet.framework.init.bootstrap.FrameworkBootstrap;
import me.piggypiglet.framework.init.builder.stages.file.FileData;
import me.piggypiglet.framework.logging.Logger;
import me.piggypiglet.framework.logging.LoggerFactory;
import me.piggypiglet.framework.registerables.StartupRegisterable;
import me.piggypiglet.framework.utils.annotations.addon.Addon;
import me.piggypiglet.framework.utils.annotations.addon.File;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class FilesRegisterable extends StartupRegisterable {
    private static final Logger<?> LOGGER = LoggerFactory.getLogger("FilesRegisterable");

    @Inject private Framework framework;
    @Inject private FileManager fileManager;
    @Inject private FrameworkBootstrap frameworkBootstrap;
    @Inject private ConfigManager configManager;

    @Override
    protected void execute() {
        final List<FileData> files = new ArrayList<>(framework.getFiles());
        Collections.sort(files);

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
                final String internalPath;

                if (f.getInternalPathReference() != null) {
                    final FileData.ConfigPathReference path = f.getInternalPathReference();

                    String value = fileManager.getConfig(path.getConfig()).getString(path.getPath(), path.getDef());

                    if (path.getMapper() != null) {
                        value = path.getMapper().apply(value);
                    }

                    internalPath = "/" + value;
                } else {
                    internalPath = f.getHardInternalPath();
                }

                final String externalPath = f.getExternalPath();
                final Class<? extends Annotation> annotationClass = f.getAnnotationClass();
                final Annotation annotationInstance = f.getAnnotationInstance();

                if (f.isConfig()) {
                    bind(FileConfiguration.class, annotationClass, annotationInstance, fileManager.loadConfig(name, internalPath, externalPath));
                } else {
                    bind(FileWrapper.class, annotationClass, annotationInstance, fileManager.loadFile(name, internalPath, externalPath));
                }
            } catch (Exception e) {
                LOGGER.error(e);
            }
        });
    }

    private <T> void bind(Class<? super T> interfaze, Class<? extends Annotation> annotationClass, Annotation annotationInstance, T instance) {
        if (annotationClass == null) {
            addAnnotatedBinding(interfaze, annotationInstance, instance);
        } else {
            addAnnotatedBinding(interfaze, annotationClass, instance);
        }
    }
}
