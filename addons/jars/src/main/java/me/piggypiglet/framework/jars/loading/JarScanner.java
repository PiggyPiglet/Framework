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

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class JarScanner {
    private final Predicate<Class<?>> match;

    public JarScanner(final Predicate<Class<?>> match) {
        this.match = match;
    }

    public CompletableFuture<Class<?>> scan(URI uri) {
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
        } catch (Exception e) {
            e.printStackTrace();
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
