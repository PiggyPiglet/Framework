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

package me.piggypiglet.framework.jars.loading;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import me.piggypiglet.framework.jars.loading.framework.Jar;
import me.piggypiglet.framework.jars.loading.framework.Loader;
import me.piggypiglet.framework.jars.loading.framework.ScannableLoader;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Singleton
public final class JarManager {
    @Inject private JarLoader jarLoader;

    private final Map<Loader, Jar[]> loaders = new HashMap<>();
    private final Map<Jar, URLClassLoader> classLoaders = new HashMap<>();
    private final Map<Jar, Object> mains = new HashMap<>();

    public JarManager load() throws Exception {
        for (Loader l : loaders.keySet()) {
            final File dir = new File(l.getDir());
            dir.getParentFile().mkdirs();

            final File[] files = dir.listFiles((d, name) -> name.endsWith(".jar"));

            if (files == null) return this;

            Jar[] jars = l.process(files);
            loaders.put(l, jars);

            for (Jar jar : jars) {
                classLoaders.put(jar, jarLoader.load(jar));
            }
        }

        return this;
    }

    public void scan(@NotNull final Injector injector) throws Exception {
        for (Map.Entry<Loader, Jar[]> entry : loaders.entrySet()) {
            if (entry.getKey() instanceof ScannableLoader) {
                ScannableLoader<?> loader = (ScannableLoader<?>) entry.getKey();
                loader.preRun();

                if (entry.getValue() == null) continue;

                for (Jar jar : entry.getValue()) {
                    final CompletableFuture<Class<?>> future = new JarScanner(loader.getMatch(), classLoaders.get(jar)).scan(new File(jar.getPath()).toURI());

                    if (future != null) {
                        final Injector newInjector = injector.createChildInjector(new AbstractModule(){});

                        future.whenComplete((c, t) -> {
                            final Object main = newInjector.getInstance(c);

                            mains.put(jar, main);
                            loader.enable(main, jar);
                        });
                    }
                }
            }
        }
    }

    public void unload(Jar jar) throws Exception {
        loaders.entrySet().stream()
                .filter(e -> Arrays.asList(e.getValue()).contains(jar))
                .findAny().ifPresent(e -> {
                    if (e.getKey() instanceof ScannableLoader<?>) {
                        ((ScannableLoader<?>) e.getKey()).disable(mains.get(jar));
                        mains.remove(jar);
                    }
        });

        jarLoader.unload(jar, classLoaders.get(jar));
        classLoaders.remove(jar);
    }

    public void add(Loader loader) {
        loaders.put(loader, null);
    }
}
