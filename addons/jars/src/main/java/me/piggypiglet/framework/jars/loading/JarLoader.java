package me.piggypiglet.framework.jars.loading;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.piggypiglet.framework.logging.Logger;
import me.piggypiglet.framework.logging.LoggerFactory;
import me.piggypiglet.framework.utils.FileUtils;
import me.piggypiglet.framework.utils.annotations.Main;

import java.io.*;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
@Singleton
public final class JarLoader {
    @Inject @Main private Object main;

    private static final Method ADD_URL_METHOD;
    private static final Map<Class, Loader> loaders = new HashMap<>();

    static {
        try {
            ADD_URL_METHOD = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
            ADD_URL_METHOD.setAccessible(true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @SafeVarargs
    @SuppressWarnings("unchecked")
    public final <T> void loadAll(String dir, T... datas) {
        if (datas.length == 0) return;

        System.out.println(loaders);

        loaders.get(datas[0].getClass()).run(dir, datas);
    }

    @SuppressWarnings({"ResultOfMethodCallIgnored"})
    void load(Jar j, String dir) {
        dir = dir.replace("/", File.separator);
        Logger logger = LoggerFactory.getLogger("JarLoader");
        String name = j.getFormattedName();
        File lib = new File(dir, name + ".jar");

        logger.info(String.format("Loading jar %s from %s with hash %s", name, j.getUrl().toString(), j.getHash()));

        if (!lib.exists()) {
            lib.getParentFile().mkdirs();

            try {
                logger.info("jar " + name + " is not currently downloaded, downloading now.");
                URL url = j.getUrl();
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");

                try (InputStream is = connection.getInputStream()) {
                    FileOutputStream out = new FileOutputStream(lib);
                    copy(is, out);
                    out.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (!lib.exists()) {
            throw new RuntimeException("Unable to download jar: " + name);
        }

        if (!FileUtils.md5Checksum(lib).equals(j.getHash())) {
            throw new RuntimeException("Hash doesn't match on " + name);
        }

        // a custom solution will be necessary eventually, as this usage of urlclassloader is removed in java 9 with no replacement
        URLClassLoader loader = (URLClassLoader) main.getClass().getClassLoader();

        try {
            ADD_URL_METHOD.invoke(loader, lib.toURI().toURL());
        } catch (Exception e) {
            throw new RuntimeException("Unable to load jar: " + name);
        }

        logger.info("Successfully loaded jar " + name);
    }

    /**
     * Copy the downloaded dependencies (jars) to it's respected place
     * @param input typically data of the jar
     * @param output typically an outputted jar file
     * @throws IOException typically can happen if there's an issue writing the data for the jars
     */
    private void copy(InputStream input, OutputStream output) throws IOException {
        byte[] buf = new byte[1024];
        int n = input.read(buf);

        while (n >= 0) {
            output.write(buf, 0, n);
            n = input.read(buf);
        }

        output.flush();
    }

    public Map<Class, Loader> getLoaders() {
        return loaders;
    }

    /**
     * Represents the loader class to handle jars
     * @param <T> loader
     */

}