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

package me.piggypiglet.framework.nukkit.file;

import cn.nukkit.utils.Config;
import cn.nukkit.utils.ConfigSection;
import com.google.common.io.Files;
import me.piggypiglet.framework.file.framework.AbstractFileConfiguration;
import me.piggypiglet.framework.file.framework.FileConfiguration;
import me.piggypiglet.framework.utils.StringUtils;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class NukkitFileConfiguration extends AbstractFileConfiguration {
    private Config config;

    public NukkitFileConfiguration() {
        super(s -> StringUtils.anyEndWith(s, ".properties", ".con", ".conf", ".config", ".yml", ".yaml", ".txt", ".list", ".enum"));
    }

    @SuppressWarnings("UnstableApiUsage")
    private NukkitFileConfiguration(Map<String, Object> map) {
        this();

        config = new Config(
                getFile(),
                Config.format.get(Files.getFileExtension(getFile().getName())),
                new ConfigSection((LinkedHashMap<String, Object>) map)
        );
    }

    @Override
    protected void internalLoad(File file, String fileContent) {
        config = new Config(file);
    }

    @Override
    protected Map<String, Object> retrieveAll() {
        return config.getAll();
    }

    @Override
    public Object get(String path) {
        return config.get(path);
    }

    @Override
    public FileConfiguration getConfigSection(String path) {
        ConfigSection section = config.getSection(path);

        if (section != null) {
            return sectionToFileConfiguration(section);
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

    private FileConfiguration sectionToFileConfiguration(ConfigSection section) {
        return new NukkitFileConfiguration(section.getAllMap());
    }
}
