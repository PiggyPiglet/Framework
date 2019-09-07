package me.piggypiglet.framework.jars.loading.web.update;

import com.google.common.collect.ImmutableList;
import me.piggypiglet.framework.jars.loading.web.DownloadableJar;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class OutdatedJarScanner {
    private final File directory;
    private final List<String> files;
    private ImmutableList<File> needsUpdating = null;

    public OutdatedJarScanner(File directory, DownloadableJar... files) throws FileNotFoundException {
        this.directory = directory;
        this.files = Arrays.stream(files).map(DownloadableJar::getFormattedName).collect(Collectors.toList());

        runChecks();
    }

    private void runChecks() throws FileNotFoundException {
        if (directory == null || !directory.exists()) throw new FileNotFoundException("Jar directory does not exist!");
    }

    public OutdatedJarScanner scan() {
        files.replaceAll(this::cutJarAtVersion);
        File[] files = directory.listFiles(f -> !this.files.contains(cutJarAtVersion(f.getName().replace(".jar", ""))));

        if (files != null) {
            needsUpdating = ImmutableList.copyOf(files);
        }

        return this;
    }

    public ImmutableList<File> getNeedsUpdating() {
        return needsUpdating;
    }

    private String cutJarAtVersion(String fileName) {
        String[] split = fileName.split("-");
        return split[split.length - 1];
    }
}
