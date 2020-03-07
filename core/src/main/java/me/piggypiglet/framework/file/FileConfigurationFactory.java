/*
 * MIT License
 *
 * Copyright (c) 2019-2020 PiggyPiglet
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package me.piggypiglet.framework.file;

import com.google.inject.Singleton;
import me.piggypiglet.framework.file.exceptions.RPFFileException;
import me.piggypiglet.framework.file.exceptions.config.BadConfigTypeException;
import me.piggypiglet.framework.file.exceptions.config.UnknownConfigTypeException;
import me.piggypiglet.framework.file.framework.FileConfiguration;
import me.piggypiglet.framework.file.framework.MapFileConfiguration;
import me.piggypiglet.framework.utils.FileUtils;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

@Singleton
public final class FileConfigurationFactory {
    private final Map<Predicate<String>, Class<? extends MapFileConfiguration>> configTypes = new HashMap<>();

    /**
     * Load a fileconfiguration from a file object
     * @param file File object
     * @return FileConfiguration instance
     * @throws Exception Will throw if IO, BadConfigType, or UnknownConfigType exceptions are encountered
     */
    public FileConfiguration get(File file) throws Exception {
        String fileContent = FileUtils.readFileToString(file);
        return getAFC(file.getPath()).load(file, fileContent);
    }

    /**
     * Load a fileconfiguration straight from the file content
     * @param path Path of the file
     * @param fileContent plain text file content
     * @return FileConfiguration instance
     * @throws RPFFileException Will throw if bad config type, unknown config type, or reflection issue.
     */
    public FileConfiguration get(String path, String fileContent) throws RPFFileException {
        return getAFC(path).load(null, fileContent);
    }

    private MapFileConfiguration getAFC(String path) throws RPFFileException {
        try {
            return (MapFileConfiguration) Arrays.stream(configTypes.get(configTypes.keySet().stream()
                    .filter(p -> p.test(path))
                    .findAny()
                    .orElseThrow(() -> new UnknownConfigTypeException("Unknown config type: " + path))).getConstructors())
                    .filter(c -> c.getParameterCount() == 0)
                    .findAny()
                    .orElseThrow(() -> new BadConfigTypeException("Bad config type encountered."))
                    .newInstance();
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new BadConfigTypeException(e.getMessage());
        }
    }

    /**
     * Get all config types this factory can map to
     * @return Map of AbstractFileConfigurations, with the key as a predicate testing whether a path matches a config
     */
    public Map<Predicate<String>, Class<? extends MapFileConfiguration>> getConfigTypes() {
        return configTypes;
    }
}