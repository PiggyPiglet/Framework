package me.piggypiglet.framework.jars.loading;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import me.piggypiglet.framework.jars.loading.framework.Jar;
import me.piggypiglet.framework.jars.loading.framework.Loader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
@Singleton
public final class JarManager {
    @Inject private JarLoader jarLoader;

    private final List<Loader> loaders = new ArrayList<>();

    public void load(Injector injector) {
        loaders.forEach(l -> {
            try {
                for (Jar jar : l.process(new File(l.getDir()).listFiles((dir, name) -> name.endsWith(".jar")))) {
                    jarLoader.load(jar);
                    CompletableFuture<Class<?>> future = new JarScanner(l.getMatch()).scan(new File(jar.getPath()).toURI());

                    if (future != null) {
                        future.whenComplete((c, t) -> l.run(injector.getInstance(c)));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public List<Loader> getLoaders() {
        return loaders;
    }
}
