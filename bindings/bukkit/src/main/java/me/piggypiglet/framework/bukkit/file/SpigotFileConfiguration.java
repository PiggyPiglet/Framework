package me.piggypiglet.framework.bukkit.file;

import me.piggypiglet.framework.file.framework.AbstractFileConfiguration;
import me.piggypiglet.framework.file.implementations.json.JsonFileConfiguration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.List;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class SpigotFileConfiguration extends AbstractFileConfiguration {
    private FileConfiguration config;

    public SpigotFileConfiguration() {
        super(s -> s.endsWith(".yml"));
    }

    @Override
    protected void internalLoad(File file, String fileContent) {
        config = new YamlConfiguration();

        try {
            config.load(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object get(String path) {
        return config.get(path);
    }

    @Override
    public me.piggypiglet.framework.file.framework.FileConfiguration getConfigSection(String path) {
        ConfigurationSection section = config.getConfigurationSection(path);

        if (section != null) {
            return configSectionToFileConfiguration(section);
        }

        return null;
    }

    @Override
    public String getString(String path) {
        return config.getString(path);
    }

    @Override
    public Integer getInt(String path) {
        return config.getInt(path);
    }

    @Override
    public Long getLong(String path) {
        return config.getLong(path);
    }

    @Override
    public Double getDouble(String path) {
        return config.getDouble(path);
    }

    @Override
    public Boolean getBoolean(String path) {
        return config.getBoolean(path);
    }

    @Override
    public List<String> getStringList(String path) {
        return config.getStringList(path);
    }

    @Override
    public List<me.piggypiglet.framework.file.framework.FileConfiguration> getConfigList(String path) {
        return null;
    }

    @Override
    public List<?> getList(String path) {
        return config.getList(path);
    }

    private me.piggypiglet.framework.file.framework.FileConfiguration configSectionToFileConfiguration(ConfigurationSection section) {
        return new JsonFileConfiguration(section.getValues(true));
    }
}
