package me.piggypiglet.framework.lang.objects;

import java.util.Map;

public final class LangConfig {
    private final Map<String, String> values;

    public LangConfig(Map<String, String> values) {
        this.values = values;
    }

    public Map<String, String> getValues() {
        return values;
    }

    public String get(String path) {
        return values.get(path);
    }
}