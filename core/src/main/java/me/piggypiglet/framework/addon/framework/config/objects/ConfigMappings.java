package me.piggypiglet.framework.addon.framework.config.objects;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

public final class ConfigMappings {
    private final String config;
    private final Map<String, String> mappings;

    public ConfigMappings(@NotNull final String config, @NotNull final Map<String, String> mappings) {
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
