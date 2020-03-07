package me.piggypiglet.framework.file.objects;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.UnaryOperator;

public class ConfigPathReference {
    private final String config;
    private final String path;
    private final String def;
    private final UnaryOperator<String> mapper;

    private ConfigPathReference(@NotNull final String config, @NotNull final String path,
                        @NotNull final String def, @Nullable final UnaryOperator<String> mapper) {
        this.config = config;
        this.path = path;
        this.def = def;
        this.mapper = mapper;
    }

    @NotNull
    public String getConfig() {
        return config;
    }

    @NotNull
    public String getPath() {
        return path;
    }

    @NotNull
    public String getDef() {
        return def;
    }

    @NotNull
    public Optional<UnaryOperator<String>> getMapper() {
        return Optional.ofNullable(mapper);
    }

    @NotNull
    public static ConfigPathReference of(@NotNull final String config, @NotNull final String path) {
        return of(config, path, "null");
    }

    @NotNull
    public static ConfigPathReference of(@NotNull final String config, @NotNull final String path,
                                         @NotNull final String def) {
        return of(config, path, def, null);
    }

    @NotNull
    public static ConfigPathReference of(@NotNull final String config, @NotNull final String path,
                                         @NotNull final String def, @Nullable final UnaryOperator<String> mapper) {
        return new ConfigPathReference(config, path, def, mapper);
    }
}
