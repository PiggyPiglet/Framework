package me.piggypiglet.framework.init.builder.stages.language.objects;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

public final class LanguageFileMappings {
    private final String config;
    private final Map<String, String> mappings;

    public LanguageFileMappings(@NotNull final String config, @NotNull final Map<String, String> mappings) {
        this.config = config;
        this.mappings = mappings;
    }

    @NotNull
    public String getConfig() {
        return config;
    }

    @NotNull
    public Map<String, String> getMappings() {
        return mappings;
    }
}
