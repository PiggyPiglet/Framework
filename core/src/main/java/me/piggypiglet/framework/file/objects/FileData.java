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

package me.piggypiglet.framework.file.objects;

import java.lang.annotation.Annotation;

public final class FileData {
    private final boolean config;
    private final String name;
    private final String internalPath;
    private final String externalPath;
    private final Class<? extends Annotation> annotation;

    public FileData(boolean config, String name, String internalPath, String externalPath, Class<? extends Annotation> annotation) {
        this.config = config;
        this.name = name;
        this.internalPath = internalPath;
        this.externalPath = externalPath;
        this.annotation = annotation;
    }

    public String getName() {
        return name;
    }

    public String getInternalPath() {
        return internalPath;
    }

    public String getExternalPath() {
        return externalPath;
    }

    public Class<? extends Annotation> getAnnotation() {
        return annotation;
    }

    public boolean isConfig() {
        return config;
    }
}
