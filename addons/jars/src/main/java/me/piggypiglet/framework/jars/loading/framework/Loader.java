package me.piggypiglet.framework.jars.loading.framework;

import java.io.File;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public abstract class Loader {
    private final String dir;

    protected Loader(String dir) {
        this.dir = dir;
    }

    public void preProcess(String dir) {}

    public abstract Jar[] process(File[] files);

    public String getDir() {
        return dir;
    }
}
