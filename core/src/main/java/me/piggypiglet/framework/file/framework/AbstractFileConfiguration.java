package me.piggypiglet.framework.file.framework;

import me.piggypiglet.framework.file.implementations.BlankFileConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public abstract class AbstractFileConfiguration implements FileConfiguration {
    private final Predicate<String> match;

    protected static final String NULL_STRING = "null";
    protected static final int NULL_NUM = 0;
    protected static final boolean NULL_BOOL = false;

    private File file;

    protected AbstractFileConfiguration(Predicate<String> match) {
        this.match = match;
    }

    public final AbstractFileConfiguration load(File file, String fileContent) {
        this.file = file;
        internalLoad(file, fileContent);
        return this;
    }

    protected void internalLoad(File file, String fileContent) {}

    public File getFile() {
        return file;
    }

    public Predicate<String> getMatch() {
        return match;
    }

    @SafeVarargs
    private final <T> T value(T value, T nullValue, T... def) {
        return value == null ? def.length >= 1 ? def[0] : nullValue : value;
    }

    @Override
    public Object get(String path, Object def) {
        return value(get(path), null, def);
    }

    @Override
    public FileConfiguration getConfigSection(String path, FileConfiguration def) {
        return value(getConfigSection(path), new BlankFileConfiguration(), def);
    }

    @Override
    public String getString(String path, String def) {
        return value(getString(path), NULL_STRING, def);
    }

    @Override
    public int getInt(String path, int def) {
        return value(getInt(path), NULL_NUM, def);
    }

    @Override
    public long getLong(String path, long def) {
        return value(getLong(path), (long) NULL_NUM, def);
    }

    @Override
    public double getDouble(String path, double def) {
        return value(getDouble(path), (double) NULL_NUM, def);
    }

    @Override
    public boolean getBoolean(String path, boolean def) {
        return value(getBoolean(path), NULL_BOOL, def);
    }

    @Override
    public List<String> getStringList(String path, List<String> def) {
        return value(getStringList(path), new ArrayList<>(), def);
    }

    @Override
    public List<FileConfiguration> getConfigList(String path, List<FileConfiguration> def) {
        return value(getConfigList(path), new ArrayList<>(), def);
    }

    @Override
    public List<?> getList(String path, List<?> def) {
        return value(getList(path), new ArrayList<>(), def);
    }
}