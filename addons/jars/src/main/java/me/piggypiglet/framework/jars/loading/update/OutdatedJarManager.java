package me.piggypiglet.framework.jars.loading.update;

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
                deleter.deleteAll();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}