package me.piggypiglet.framework.file.internal;

import org.jetbrains.annotations.NotNull;

public enum InternalFiles {
    CORE_LANGUAGE("core_language", "/core_language.json");

    private final String name;
    private final String path;

    InternalFiles(@NotNull final String name, @NotNull final String path) {
        this.name = name;
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }
}
