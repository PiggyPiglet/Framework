package me.piggypiglet.framework.bungeecord.file;

import me.piggypiglet.framework.file.framework.AbstractFileConfiguration;
import me.piggypiglet.framework.file.framework.FileConfiguration;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.util.List;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class BungeeFileConfiguration extends AbstractFileConfiguration {
    private Configuration config;

    public BungeeFileConfiguration() {
        super(s -> s.endsWith(".yml"));
    }

    private BungeeFileConfiguration(Configuration config, File file) {
        this();
        load(file, "");
        this.config = config;
    }

    @Override
    protected void internalLoad(File file, String fileContent) {
        try {
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object get(String path) {
        return config.get(path);
    }

    @Override
    public FileConfiguration getConfigSection(String path) {
        Configuration section = config.getSection(path);

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
    public List<FileConfiguration> getConfigList(String path) {
        return null;
    }

    @Override
    public List<?> getList(String path) {
        return config.getList(path);
    }

    private FileConfiguration configSectionToFileConfiguration(Configuration section) {
        return new BungeeFileConfiguration(section, getFile());
    }
}
