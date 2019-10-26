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

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.piggypiglet.framework.guice.objects.Injector;
import me.piggypiglet.framework.jars.loading.framework.Jar;
import me.piggypiglet.framework.jars.loading.framework.Loader;
import me.piggypiglet.framework.jars.loading.framework.ScannableLoader;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Singleton
public final class JarManager {
    @Inject private JarLoader jarLoader;

    private final Map<Loader, Jar[]> loaders = new HashMap<>();

    public JarManager load() {
        for (Loader l : loaders.keySet()) {
            Jar[] jars = l.process(new File(l.getDir()).listFiles((dir, name) -> name.endsWith(".jar")));
            loaders.put(l, jars);

            for (Jar jar : jars) {
                jarLoader.load(jar);
            }
        }

        return this;
    }

    public void scan(Injector injector) {
        for (Map.Entry<Loader, Jar[]> entry : loaders.entrySet()) {
            if (entry.getKey() instanceof ScannableLoader) {
                ScannableLoader<?> loader = (ScannableLoader<?>) entry.getKey();

                for (Jar jar : entry.getValue()) {
                    CompletableFuture<Class<?>> future = new JarScanner(loader.getMatch()).scan(new File(jar.getPath()).toURI());

                    if (future != null) {
                        future.whenComplete((c, t) -> loader.run(injector.getInstance(c)));
                    }
                }
            }
        }
    }

    public void add(Loader loader) {
        loaders.put(loader, null);
    }
}
