package me.piggypiglet.framework.file.objects;

import java.lang.annotation.Annotation;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class FileData {
    private final boolean config;
    private final String name;
    private final String internalPath;
    private final String externalPath;
    private final Class<? extends Annotation> annotation;

    public FileData(boolean config, String name, String internalPath, String externalPath, Class<? extends Annotation> annotation) {
        this.config = config;
        this.name = name;
        this.internalPath = internalPath;
        this.externalPath = externalPath;
        this.annotation = annotation;
    }

    public String getName() {
        return name;
    }

    public String getInternalPath() {
        return internalPath;
    }

    public String getExternalPath() {
        return externalPath;
    }

    public Class<? extends Annotation> getAnnotation() {
        return annotation;
    }

    public boolean isConfig() {
        return config;
    }
}
