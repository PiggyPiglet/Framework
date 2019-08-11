package me.piggypiglet.framework.file.implementations.json;

import me.piggypiglet.framework.file.framework.AbstractFileConfiguration;
import me.piggypiglet.framework.file.framework.FileConfiguration;
import me.piggypiglet.framework.json.JsonParser;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class JsonFileConfiguration extends AbstractFileConfiguration {
    private JsonParser parser;

    public JsonFileConfiguration() {}

    private JsonFileConfiguration(JsonParser parser) {
        this.parser = parser;
    }

    @Override
    protected void internalLoad(File file, String fileContent) {
        parser = new JsonParser(fileContent);
    }

    @Override
    public Object get(String path) {
        return parser.get(path);
    }

    @Override
    public FileConfiguration getConfigSection(String path) {
        return new JsonFileConfiguration(parser.getJsonSection(path));
    }

    @Override
    public String getString(String path) {
        return parser.getString(path);
    }

    @Override
    public Integer getInt(String path) {
        return parser.getInt(path);
    }

    @Override
    public Long getLong(String path) {
        return parser.getLong(path);
    }

    @Override
    public Double getDouble(String path) {
        return parser.getDouble(path);
    }

    @Override
    public Boolean getBoolean(String path) {
        return parser.getBoolean(path);
    }

    @Override
    public final List<String> getStringList(String path) {
        return parser.getStringList(path);
    }

    @Override
    public final List<FileConfiguration> getConfigList(String path) {
        List<JsonParser> jsons = parser.getJsonList(path);

        // the npe shouldn't be caused in this class
        if (jsons == null) {
            return null;
        }

        return jsons.stream().map(JsonFileConfiguration::new).collect(Collectors.toList());
    }

    @Override
    public final List<?> getList(String path) {
        return parser.getList(path);
    }
}