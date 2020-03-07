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

package me.piggypiglet.framework.file.internal.registerables;

import com.google.inject.Inject;
import me.piggypiglet.framework.Framework;
import me.piggypiglet.framework.addon.init.AddonData;
import me.piggypiglet.framework.file.FileManager;
import me.piggypiglet.framework.file.framework.FileConfiguration;
import me.piggypiglet.framework.file.objects.ConfigPathReference;
import me.piggypiglet.framework.file.objects.FileData;
import me.piggypiglet.framework.file.objects.FileWrapper;
import me.piggypiglet.framework.init.bootstrap.FrameworkBootstrap;
import me.piggypiglet.framework.init.builder.stages.file.FilesData;
import me.piggypiglet.framework.logging.LoggerFactory;
import me.piggypiglet.framework.logging.framework.Logger;
import me.piggypiglet.framework.registerables.StartupRegisterable;
import me.piggypiglet.framework.utils.annotations.wrapper.AnnotationWrapper;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class FilesRegisterable extends StartupRegisterable {
    private static final Logger<?> LOGGER = LoggerFactory.getLogger("FilesRegisterable");

    @Inject private Framework framework;
    @Inject private FileManager fileManager;
    @Inject private FrameworkBootstrap main;

    @Override
    protected void execute() {
        final List<FileData> files = new ArrayList<>(framework.getFiles().getFiles());
        main.getAddons().values().stream()
                .map(AddonData::getFiles)
                .map(FilesData::getFiles)
                .flatMap(List::stream)
                .forEach(files::add);

        Collections.sort(files);

        files.forEach(f -> {
            try {
                final String name = f.getName();
                final String internalPath = "/" + path(f.getInternalPathReference(), f.getHardInternalPath());
                final String externalPath = path(f.getExternalPathReference(), f.getHardExternalPath());
                final AnnotationWrapper annotation = f.getAnnotation();

                if (f.isConfig()) {
                    bind(FileConfiguration.class, annotation, fileManager.loadConfig(name, internalPath, externalPath));
                } else {
                    bind(FileWrapper.class, annotation, fileManager.loadFile(name, internalPath, externalPath));
                }
            } catch (Exception e) {
                LOGGER.error(e);
            }
        });
    }

    private <T> void bind(@NotNull final Class<? super T> interfaze, @NotNull final AnnotationWrapper annotation,
                          @NotNull final T instance) {
        addAnnotatedBinding(interfaze, annotation, instance);
    }

    @Contract("null, _ -> !null; _, null -> !null")
    @NotNull
    private String path(@Nullable final ConfigPathReference reference, @Nullable final String hard) {
        if (reference != null) {
            return reference.getMapper().orElse(s -> s).apply(fileManager.getConfig(reference.getConfig())
                    .orElseThrow(() -> new RuntimeException("Provided config: " + reference.getConfig() + " for config path reference doesn't exist."))
                    .getString(reference.getPath(), reference.getDef()));
        }

        return Objects.requireNonNull(hard);
    }
}
