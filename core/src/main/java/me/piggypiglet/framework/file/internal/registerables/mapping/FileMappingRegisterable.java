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

package me.piggypiglet.framework.file.internal.registerables.mapping;

import com.google.inject.Inject;
import me.piggypiglet.framework.file.FileManager;
import me.piggypiglet.framework.file.framework.FileConfiguration;
import me.piggypiglet.framework.file.framework.MapFileConfiguration;
import me.piggypiglet.framework.file.mapping.Maps;
import me.piggypiglet.framework.mapping.gson.GsonObjectMappers;
import me.piggypiglet.framework.registerables.StartupRegisterable;
import me.piggypiglet.framework.scanning.framework.Scanner;
import me.piggypiglet.framework.utils.annotations.wrapper.AnnotationWrapper;

public final class FileMappingRegisterable extends StartupRegisterable {
    @Inject private FileManager fileManager;
    @Inject private Scanner scanner;

    @Override
    protected void execute() {
        scanner.getClassesAnnotatedWith(new AnnotationWrapper(Maps.class)).forEach(c -> add(c, c.getAnnotation(Maps.class).value()));
    }

    private <T> void add(Class<T> clazz, String name) {
        final FileConfiguration config = fileManager.getConfig(name)
                .orElseThrow(() -> new RuntimeException("Provided config: " + name + " for mapping doesn't exist."));

        if (config instanceof MapFileConfiguration) {
            addBinding(clazz, GsonObjectMappers.of(clazz).dataToType(((MapFileConfiguration) config).getAll()));
        } else {
            throw new RuntimeException("Provided config: " + name + " for mapping isn't a MapFileConfiguration.");
        }
    }
}
