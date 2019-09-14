package me.piggypiglet.framework.addon;

import com.google.inject.Singleton;
import me.piggypiglet.framework.addon.objects.Config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Singleton
public final class ConfigManager {
    private final List<String> filesToBeCreated = new ArrayList<>();
    private final Map<Class<?>, Config> configs = new HashMap<>();

    public List<String> getFilesToBeCreated() {
        return filesToBeCreated;
    }

    public Map<Class<?>, Config> getConfigs() {
        return configs;
    }
}
