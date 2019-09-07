package me.piggypiglet.framework.jars.loading.web;

import me.piggypiglet.framework.jars.loading.framework.Jar;

import java.net.URL;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class DownloadableJar extends Jar {
    private final String name;
    private final String path;
    private final String version;
    private final String hash;
    private final URL url;

    public DownloadableJar(String name, String path, String version, String hash, URL url) {
        this.name = name;
        this.path = path;
        this.version = version;
        this.hash = hash;
        this.url = url;
    }

    @Override
    public String getName() {
        return name;
    }

    public String getFormattedName() {
        return getName() + "-" + getVersion();
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public String getVersion() {
        return version;
    }

    public String getHash() {
        return hash;
    }

    public URL getUrl() {
        return url;
    }
}
