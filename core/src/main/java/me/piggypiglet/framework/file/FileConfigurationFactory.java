package me.piggypiglet.framework.file;

import me.piggypiglet.framework.file.exceptions.UnknownConfigTypeException;
import me.piggypiglet.framework.file.framework.AbstractFileConfiguration;
import me.piggypiglet.framework.file.framework.FileConfiguration;
import me.piggypiglet.framework.file.implementations.json.JsonFileConfiguration;
import me.piggypiglet.framework.utils.FileUtils;

import java.io.File;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class FileConfigurationFactory {
    public static FileConfiguration get(File file) throws Exception {
        String fileContent = FileUtils.readFileToString(file);
        return getAFC(file.getPath()).load(file, fileContent);
    }

    public static FileConfiguration get(String path, String fileContent) throws Exception {
        return getAFC(path).load(null, fileContent);
    }

    private static AbstractFileConfiguration getAFC(String path) throws Exception {
        String[] pathBits = path.toLowerCase().split("\\.");

        AbstractFileConfiguration fileConfiguration;

        switch (pathBits[pathBits.length - 1]) {
            case "json": fileConfiguration = new JsonFileConfiguration(); break;
            default: throw new UnknownConfigTypeException("Unknown config type: " + pathBits[pathBits.length - 1]);
        }

        return fileConfiguration;
    }
}