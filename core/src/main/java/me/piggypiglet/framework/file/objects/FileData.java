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

import me.piggypiglet.framework.utils.annotations.wrapper.AnnotationWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class FileData implements Comparable<FileData> {
    private final boolean config;
    private final String name;
    private final String hardInternalPath;
    private final ConfigPathReference internalPathReference;
    private final String hardExternalPath;
    private final ConfigPathReference externalPathReference;
    private final AnnotationWrapper annotation;
    private final ConfigData relocations = new ConfigData();

    public FileData(final boolean config, @NotNull final String name,
                     @Nullable final String hardInternalPath, @Nullable final ConfigPathReference internalPathReference,
                     @Nullable final String hardExternalPath, @Nullable final ConfigPathReference externalPathReference,
                     @NotNull final AnnotationWrapper annotation) {
        this.config = config;
        this.name = name;
        this.hardInternalPath = hardInternalPath;
        this.internalPathReference = internalPathReference;
        this.hardExternalPath = hardExternalPath;
        this.externalPathReference = externalPathReference;
        this.annotation = annotation;
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

    @NotNull
    public AnnotationWrapper getAnnotation() {
        return annotation;
    }

    @NotNull
    public ConfigData getRelocations() {
        return relocations;
    }

    @Override
    public int compareTo(@NotNull final FileData fileData) {
        if (fileData.hardInternalPath == null || fileData.hardExternalPath == null) {
            return -1;
        }

        return 1;
    }

}
