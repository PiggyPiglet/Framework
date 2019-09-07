package me.piggypiglet.framework.jars.loading.web.update;

import me.piggypiglet.framework.jars.loading.web.DownloadableJar;
import me.piggypiglet.framework.logging.LoggerFactory;

import java.io.File;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class OutdatedJarManager {
    /**
     * Delete any outdated jars
     * @param dir Directory to look in
     * @param files Current files
     */
    public static void deleteOutdated(String dir, DownloadableJar... files) {
        try {
            OutdatedJarDeleter deleter = new OutdatedJarDeleter(
                    new OutdatedJarScanner(new File(dir), files).scan().getNeedsUpdating()
            );

            if (deleter.needsUpdating()) {
                LoggerFactory.getLogger("OutdatedJarManager").info("Deleting outdated/irrelevant jars in %s.", dir);
                deleter.deleteAll();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}