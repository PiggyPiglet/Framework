package me.piggypiglet.framework.jars.loading.web;

import me.piggypiglet.framework.jars.loading.web.update.OutdatedJarManager;
import me.piggypiglet.framework.logging.Logger;
import me.piggypiglet.framework.logging.LoggerFactory;
import me.piggypiglet.framework.utils.FileUtils;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------

public final class JarDownloader {
    private static final Logger LOGGER = LoggerFactory.getLogger("JarDownloader");

    public static void download(String dir, DownloadableJar... jars) {
        OutdatedJarManager.deleteOutdated(dir, jars);

        for (DownloadableJar jar : jars) {
            dir = dir.replace("/", File.separator);
            final String name = jar.getFormattedName();
            final File file = new File(dir, name + ".jar");
            final URL url = jar.getUrl();
            final String hash = jar.getHash();

            if (!file.exists()) {
                //noinspection ResultOfMethodCallIgnored
                file.getParentFile().mkdirs();

                try {
                    LOGGER.info("Downloading jar %s from %s with hash %s.", name, url.toString(), hash);

                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");

                    try (InputStream is = connection.getInputStream()) {
                        Files.copy(is, Paths.get(file.toURI()), StandardCopyOption.REPLACE_EXISTING);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (!file.exists()) {
                LOGGER.error("Unable to download %s", name);
                return;
            }

            if (!FileUtils.md5Checksum(file).equals(hash)) {
                LOGGER.error("Hash doesn't match on %s", name);
                return;
            }

            LOGGER.info("Successfully downloaded %s", name);
        }
    }
}
