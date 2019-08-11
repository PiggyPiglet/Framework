package me.piggypiglet.framework.file.implementations;

import me.piggypiglet.framework.file.framework.AbstractFileConfiguration;
import me.piggypiglet.framework.file.framework.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class BlankFileConfiguration extends AbstractFileConfiguration {
    @Override
    public Object get(String path) {
        return null;
    }

    @Override
    public FileConfiguration getConfigSection(String path) {
        return this;
    }

    @Override
    public String getString(String path) {
        return NULL_STRING;
    }

    @Override
    public Integer getInt(String path) {
        return NULL_NUM;
    }

    @Override
    public Long getLong(String path) {
        return (long) NULL_NUM;
    }

    @Override
    public Double getDouble(String path) {
        return (double) NULL_NUM;
    }

    @Override
    public Boolean getBoolean(String path) {
        return NULL_BOOL;
    }

    @Override
    public List<String> getStringList(String path) {
        return new ArrayList<>();
    }

    @Override
    public List<FileConfiguration> getConfigList(String path) {
        return new ArrayList<>();
    }

    @Override
    public List<?> getList(String path) {
        return new ArrayList<>();
    }
}