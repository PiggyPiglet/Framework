package me.piggypiglet.framework.file;

import com.google.inject.Singleton;
import me.piggypiglet.framework.file.exceptions.BadConfigTypeException;
import me.piggypiglet.framework.file.exceptions.UnknownConfigTypeException;
import me.piggypiglet.framework.file.framework.AbstractFileConfiguration;
import me.piggypiglet.framework.file.framework.FileConfiguration;
import me.piggypiglet.framework.utils.FileUtils;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
@Singleton
public final class FileConfigurationFactory {
    private final Map<Predicate<String>, Class<? extends AbstractFileConfiguration>> configTypes = new HashMap<>();

    /**
     * Load a fileconfiguration from a file object
     */
    public FileConfiguration get(File file) throws Exception {
        String fileContent = FileUtils.readFileToString(file);
        return getAFC(file.getPath()).load(file, fileContent);
    }

    /**
     * Load a fileconfiguration straight from the file content
     * @param path Path of the file
     */
    public FileConfiguration get(String path, String fileContent) throws Exception {
        return getAFC(path).load(null, fileContent);
    }

    private AbstractFileConfiguration getAFC(String path) throws Exception {
        return (AbstractFileConfiguration) Arrays.stream(configTypes.get(configTypes.keySet().stream()
                .filter(p -> p.test(path))
                .findAny()
                .orElseThrow(() -> new UnknownConfigTypeException("Unknown config type: " + path))).getConstructors())
                .filter(c -> c.getParameterCount() == 0)
                .findAny()
                .orElseThrow(() -> new BadConfigTypeException("Bad config type encountered."))
                .newInstance();
    }

    /**
     * Get all config types this factory can map to
     * @return Map of AbstractFileConfigurations, with the key as a predicate testing whether a path matches a config
     */
    public Map<Predicate<String>, Class<? extends AbstractFileConfiguration>> getConfigTypes() {
        return configTypes;
    }
}