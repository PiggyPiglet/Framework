package me.piggypiglet.framework.jars.loading;

import com.google.inject.Inject;
import me.piggypiglet.framework.jars.loading.framework.Jar;
import me.piggypiglet.framework.logging.Logger;
import me.piggypiglet.framework.logging.LoggerFactory;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class JarLoader {
    @Inject private URLClassLoader urlClassLoader;

    private static final Logger LOGGER = LoggerFactory.getLogger("JarLoader");
    private static final Method ADD_URL_METHOD;

    static {
        try {
            ADD_URL_METHOD = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
            ADD_URL_METHOD.setAccessible(true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void load(Jar jar) {
        String name = jar.getName();
        File file = new File(jar.getPath());

        LOGGER.info("Loading jar %s.", name);

        if (!file.exists()) {
            LOGGER.error("Jar doesn't exist for %s.", name);
            return;
        }

        try {
            ADD_URL_METHOD.invoke(urlClassLoader, file.toURI().toURL());
        } catch (Exception e) {
            e.printStackTrace();
        }

        LOGGER.info("Successfully loaded jar %s", name);
    }
}
