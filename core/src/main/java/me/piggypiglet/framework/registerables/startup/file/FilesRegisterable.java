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
