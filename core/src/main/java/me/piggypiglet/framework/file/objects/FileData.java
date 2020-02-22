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

package me.piggypiglet.framework.file.objects;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.annotation.Annotation;
import java.util.function.UnaryOperator;

public final class FileData implements Comparable<FileData> {
    private final boolean config;
    private final String name;
    private final String hardInternalPath;
    private final ConfigPathReference internalPathReference;
    private final String hardExternalPath;
    private final ConfigPathReference externalPathReference;
    private final Class<? extends Annotation> annotationClass;
    private final Annotation annotationInstance;

    public FileData(final boolean config, @NotNull final String name,
                     @Nullable final String hardInternalPath, @Nullable final ConfigPathReference internalPathReference,
                     @Nullable final String hardExternalPath, @Nullable final ConfigPathReference externalPathReference,
                     @Nullable final Class<? extends Annotation> annotationClass, @Nullable final Annotation annotationInstance) {
        this.config = config;
        this.name = name;
        this.hardInternalPath = hardInternalPath;
        this.internalPathReference = internalPathReference;
        this.hardExternalPath = hardExternalPath;
        this.externalPathReference = externalPathReference;
        this.annotationClass = annotationClass;
        this.annotationInstance = annotationInstance;
    }

    public boolean isConfig() {
        return config;
    }

    @NotNull
    public String getName() {
        return name;
    }

    @Nullable
    public String getHardInternalPath() {
        return hardInternalPath;
    }

    @Nullable
    public ConfigPathReference getInternalPathReference() {
        return internalPathReference;
    }

    @Nullable
    public String getHardExternalPath() {
        return hardExternalPath;
    }

    @Nullable
    public ConfigPathReference getExternalPathReference() {
        return externalPathReference;
    }

    @Nullable
    public Class<? extends Annotation> getAnnotationClass() {
        return annotationClass;
    }

    @Nullable
    public Annotation getAnnotationInstance() {
        return annotationInstance;
    }

    @Override
    public int compareTo(@NotNull final FileData fileData) {
        if (fileData.hardInternalPath == null) {
            return -1;
        }

        return 1;
    }

    public static class ConfigPathReference {
        private final String config;
        private final String path;
        private final String def;
        private final UnaryOperator<String> mapper;

        private ConfigPathReference(@NotNull final String config, @NotNull final String path,
                                    @NotNull final String def, @Nullable final UnaryOperator<String> mapper) {
            this.config = config;
            this.path = path;
            this.def = def;
            this.mapper = mapper;
        }

        @NotNull
        public String getConfig() {
            return config;
        }

        @NotNull
        public String getPath() {
            return path;
        }

        @NotNull
        public String getDef() {
            return def;
        }

        @Nullable
        public UnaryOperator<String> getMapper() {
            return mapper;
        }

        @NotNull
        public static ConfigPathReference of(@NotNull final String config, @NotNull final String path) {
            return of(config, path, "null");
        }

        @NotNull
        public static ConfigPathReference of(@NotNull final String config, @NotNull final String path,
                                             @NotNull final String def) {
            return of(config, path, def, null);
        }

        @NotNull
        public static ConfigPathReference of(@NotNull final String config, @NotNull final String path,
                                             @NotNull final String def, @Nullable final UnaryOperator<String> mapper) {
            return new ConfigPathReference(config, path, def, mapper);
        }
    }
}
