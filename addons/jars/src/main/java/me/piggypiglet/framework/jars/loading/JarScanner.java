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

package me.piggypiglet.framework.jars.loading;

import java.io.BufferedInputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;
import java.util.jar.JarInputStream;
import java.util.zip.ZipEntry;

public final class JarScanner {
    private final Predicate<Class<?>> match;

    public JarScanner(final Predicate<Class<?>> match) {
        this.match = match;
    }

    public CompletableFuture<Class<?>> scan(URI uri) throws Exception {
        final Path path = Paths.get(uri);
        final List<String> classes = new ArrayList<>();

        try (JarInputStream jar = new JarInputStream(new BufferedInputStream(Files.newInputStream(path)))) {
            ZipEntry entry = jar.getNextEntry();

            if (entry == null) {
                return null;
            }

            do {
                final String name = entry.getName();

                if (name.toLowerCase().endsWith(".class")) {
                    classes.add(name);
                }
            } while ((entry = jar.getNextEntry()) != null);
        }

        CompletableFuture<Class<?>> module = new CompletableFuture<>();

        classes.stream().parallel()
                .map(s -> s = s
                        .replace(".class", "")
                        .replace("/", "."))
                .forEach(c -> {
                    Class clazz = null;

                    try {
                        clazz = Class.forName(c);
                    } catch (Exception ignored) {}

                    if (clazz != null) {
                        if (match.test(clazz)) {
                            module.complete(clazz);
                        }
                    }
                });

        return module;
    }
}
