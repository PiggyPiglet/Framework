/*
 * MIT License
 *
 * Copyright (c) 2019 PiggyPiglet
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

package me.piggypiglet.framework.bukkit.file;

import me.piggypiglet.framework.file.framework.AbstractFileConfiguration;
import me.piggypiglet.framework.file.implementations.json.JsonFileConfiguration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.List;
import java.util.Map;

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

    @Override
    protected Map<String, Object> retrieveAll() {
        return config.getValues(true);
    }

    private me.piggypiglet.framework.file.framework.FileConfiguration configSectionToFileConfiguration(ConfigurationSection section) {
        return new JsonFileConfiguration(section.getValues(true));
    }
}
