package me.piggypiglet.framework.addon.objects;

import java.util.Map;

public final class Config {
    private final Map<String, Object> items;

    public Config(Map<String, Object> items) {
        this.items = items;
    }

    public Map<String, Object> getItems() {
        return items;
    }
}
