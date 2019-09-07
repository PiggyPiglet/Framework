package me.piggypiglet.framework.jars.loading;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import me.piggypiglet.framework.jars.loading.framework.Jar;
import me.piggypiglet.framework.jars.loading.framework.Loader;
import me.piggypiglet.framework.jars.loading.framework.ScannableLoader;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
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
