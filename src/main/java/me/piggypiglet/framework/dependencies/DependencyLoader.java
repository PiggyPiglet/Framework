package me.piggypiglet.framework.dependencies;

import lombok.Data;
import me.piggypiglet.framework.Framework;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.util.logging.Logger;

// ------------------------------
// Copyright (c) PiggyPiglet 2018
// https://www.piggypiglet.me
// Credit to lucko, https://lucko.me luck@lucko.me
// ------------------------------
public final class DependencyLoader {
    private static final Method ADD_URL_METHOD;

    static {
        try {
            ADD_URL_METHOD = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
            ADD_URL_METHOD.setAccessible(true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void loadAll(Class<?> clazz) {
        MavenLibrary[] libs = clazz.getDeclaredAnnotationsByType(MavenLibrary.class);

        if (libs != null) {
            for (MavenLibrary lib : libs) {
                load(new Dependency(lib.groupId(), lib.artifactId(), lib.version(), lib.repo()));
            }
        }
    }

    @SuppressWarnings({"ConstantConditions", "ResultOfMethodCallIgnored"})
    private static void load(Dependency d) {
        Logger logger = Logger.getLogger("[" + Framework.getProgramName() + "]");
        String name = d.getArtifactId() + "-" + d.getVersion();
        Object main = Framework.getMain();
        File lib;

        if (main instanceof JavaPlugin) {
            lib = new File(((JavaPlugin) main).getDataFolder() + "/libs", name + ".jar");
        } else {
            lib = new File("/libs", name + ".jar");
        }

        logger.info(String.format("Loading dependency %s:%s:%s from %s", d.getGroupId(), d.getArtifactId(), d.getVersion(), d.getRepoUrl()));

        if (!lib.exists()) {
            lib.getParentFile().mkdirs();

            try {
                logger.info("Dependency " + name + " is not currently downloaded, downloading now.");
                URL url = d.getUrl();

                try (InputStream is = url.openStream()) {
                    Files.copy(is, lib.toPath());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (!lib.exists()) {
            throw new RuntimeException("Unable to download dependency: " + d.toString());
        }

        URLClassLoader loader = (URLClassLoader) main.getClass().getClassLoader();
        try {
            ADD_URL_METHOD.invoke(loader, lib.toURI().toURL());
        } catch (Exception e) {
            throw new RuntimeException("Unable to load dependency: " + d.toString());
        }

        logger.info("Successfully loaded dependency " + name);
    }

    @Data
    private static final class Dependency {
        private final String groupId;
        private final String artifactId;
        private final String version;
        private final String repoUrl;

        public URL getUrl() {
            String repo = repoUrl;

            if (!repo.endsWith("/")) {
                repo += "/";
            }

            repo += "%s/%s/%s/%s-%s.jar";

            try {
                return new URL(String.format(repo, groupId.replace(".", "/"), artifactId, version, artifactId, version));
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}
