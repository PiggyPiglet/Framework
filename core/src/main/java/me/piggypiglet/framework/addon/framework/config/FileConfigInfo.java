package me.piggypiglet.framework.addon.framework.config;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public final class FileConfigInfo {
    private final String config;
    private Map<String, String> mappings = new HashMap<>();
    private Map<String, String> values = new HashMap<>();

    public FileConfigInfo(@NotNull final String config) {
        this.config = config;
    }

    @NotNull
    public String getConfig() {
        return config;
    }

    @NotNull
    public Map<String, String> getMappings() {
        return mappings;
    }

    void setMappings(@NotNull final Map<String, String> mappings) {
        this.mappings = mappings;
    }

    @NotNull
    public Map<String, String> getValues() {
        return values;
    }

    void setValues(@NotNull final Map<String, String> values) {
        this.values = values;
    }
}
