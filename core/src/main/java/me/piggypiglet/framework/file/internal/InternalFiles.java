package me.piggypiglet.framework.file.internal;

import org.jetbrains.annotations.NotNull;

public enum InternalFiles {
    CORE_LANGUAGE("core_language", "core_language.json", true);

    private final String name;
    private final String path;
    private final boolean config;

    InternalFiles(@NotNull final String name, @NotNull final String path, final boolean config) {
        this.name = name;
        this.path = path;
        this.config = config;
    }

    @NotNull
    public String getName() {
        return name;
    }

    @NotNull
    public String getPath() {
        return path;
    }

    public boolean isConfig() {
        return config;
    }
}
