package me.piggypiglet.framework.jars.loading.web.update;

import com.google.common.collect.ImmutableList;

import java.io.File;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class OutdatedJarDeleter {
    private final ImmutableList<File> filesToUpdate;

    public OutdatedJarDeleter(ImmutableList<File> filesToUpdate) {
        this.filesToUpdate = filesToUpdate;
    }

    public boolean needsUpdating() {
        return filesToUpdate != null && filesToUpdate.size() > 0;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void deleteAll() {
        filesToUpdate.forEach(File::delete);
    }
}