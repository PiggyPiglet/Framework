package me.piggypiglet.framework.addon.objects;

import java.util.Map;

public final class ConfigInfo {
    private final String config;
    private final Map<String, String> locations;
    private final boolean def;

    public ConfigInfo(String config, Map<String, String> locations, boolean def) {
        this.config = config;
        this.locations = locations;
        this.def = def;
    }

    public String getConfig() {
        return config;
    }

    public Map<String, String> getLocations() {
        return locations;
    }

    public boolean isDefault() {
        return def;
    }
}
