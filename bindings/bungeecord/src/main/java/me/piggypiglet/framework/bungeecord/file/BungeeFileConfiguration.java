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

package me.piggypiglet.framework.bungeecord.file;

import me.piggypiglet.framework.file.framework.AbstractFileConfiguration;
import me.piggypiglet.framework.file.framework.FileConfiguration;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.util.List;

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
