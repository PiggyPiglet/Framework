package me.piggypiglet.framework.file.framework;

import java.util.List;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
@SuppressWarnings("unused")
public interface FileConfiguration {
    Object get(String path);

    Object get(String path, Object def);

    FileConfiguration getConfigSection(String path);

    FileConfiguration getConfigSection(String path, FileConfiguration def);

    String getString(String path);

    String getString(String path, String def);

    Integer getInt(String path);

    int getInt(String path, int def);

    Long getLong(String path);

    long getLong(String path, long def);

    Double getDouble(String path);

    double getDouble(String path, double def);

    Boolean getBoolean(String path);

    boolean getBoolean(String path, boolean def);

    List<String> getStringList(String path);

    List<String> getStringList(String path, List<String> def);

    List<FileConfiguration> getConfigList(String path);

    List<FileConfiguration> getConfigList(String path, List<FileConfiguration> def);

    List<?> getList(String path);

    List<?> getList(String path, List<?> def);
}