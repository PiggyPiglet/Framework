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

package me.piggypiglet.framework.file.framework.implementations.map;

import me.piggypiglet.framework.file.exceptions.config.ConfigSaveException;
import me.piggypiglet.framework.file.framework.AbstractFileConfiguration;
import me.piggypiglet.framework.file.framework.FileConfiguration;
import me.piggypiglet.framework.file.framework.MutableFileConfiguration;
import me.piggypiglet.framework.file.framework.objects.Mapper;
import me.piggypiglet.framework.utils.map.Maps;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class MapFileConfiguration extends AbstractFileConfiguration implements MutableFileConfiguration {
    private Map<String, Object> items;
    private Map<String, Object> flat;

    protected MapFileConfiguration(Predicate<String> match) {
        super(match);
    }

    protected MapFileConfiguration(Predicate<String> match, Mapper... mappers) {
        super(match, mappers);
    }

    MapFileConfiguration(Map<String, Object> items) {
        super(null);
        this.items = items;
        flat = getAll();
    }

    protected abstract Map<String, Object> provide(java.io.File file, String fileContent);

    protected abstract String convert(Map<String, Object> items);

    @Override
    protected void internalLoad(java.io.File file, String fileContent) {
        items = provide(file, fileContent);
        mappers.forEach(f -> items = Maps.map(items, f.getClazz(), f.getFunction()));
        flat = getAll();
    }

    @Override
    public Object get(String path) {
        return Maps.recursiveGet(items, path);
    }

    @SuppressWarnings("unchecked")
    @Override
    public FileConfiguration getConfigSection(String path) {
        return configSection((Map<String, Object>) get(path));
    }

    @Override
    public String getString(String path) {
        return (String) flat.get(path);
    }

    @Override
    public Integer getInt(String path) {
        return number(path, Number::intValue);
    }

    @Override
    public Long getLong(String path) {
        return number(path, Number::longValue);
    }

    @Override
    public Double getDouble(String path) {
        return number(path, Number::doubleValue);
    }

    private <T> T number(String path, Function<Number, T> value) {
        final Object obj = flat.get(path);
        return obj == null ? null : value.apply(((Number) obj));
    }

    @Override
    public Boolean getBoolean(String path) {
        return (Boolean) flat.get(path);
    }

    @Override
    public List<String> getStringList(String path) {
        return getList(path);
    }

    @Override
    public List<FileConfiguration> getConfigList(String path) {
        final List<Map<String, Object>> list = getList(path);

        if (list != null && !list.isEmpty()) {
            return list.stream()
                    .map(this::configSection)
                    .collect(Collectors.toList());
        }

        return new ArrayList<>();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> List<T> getList(String path) {
        return (List<T>) flat.get(path);
    }

    @Override
    protected Map<String, Object> retrieveAll() {
        return items;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void set(String path, Object value) {
        final String[] split = path.split("\\.");

        if (split.length == 0) {
            items = new HashMap<>();
            flat = new HashMap<>();
            return;
        }

        final String key = split[split.length - 1];
        final String parentPath = String.join(".", Arrays.copyOf(split, split.length - 1));
        Map<String, Object> parent = split.length == 1 ? items : (Map<String, Object>) get(parentPath);

        if (parent == null) {
            set(parentPath, new HashMap<>());
            parent = (Map<String, Object>) get(parentPath);
        }

        if (value == null) {
            parent.remove(key);
            new HashSet<>(flat.keySet()).stream().filter(o -> o.startsWith(path)).forEach(flat::remove);
        } else {
            parent.put(key, value);
            flat.put(path, value);
        }
    }

    @Override
    public void save() throws ConfigSaveException {
        final String content = convert(items);

        try {
            Files.write(Paths.get(getFile().toURI()), Arrays.asList(content.split("\n")), StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new ConfigSaveException(e.getMessage());
        }
    }

    private FileConfiguration configSection(Map<String, Object> items) {
        return items == null ? null : new MapFileConfigurationSection(items);
    }
}