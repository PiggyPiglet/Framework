package me.piggypiglet.framework.jars.loading.update;

import me.piggypiglet.framework.logging.LoggerFactory;

import java.io.File;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class OutdatedJarManager {
    public static void deleteOutdated(String dir, String... files) {
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