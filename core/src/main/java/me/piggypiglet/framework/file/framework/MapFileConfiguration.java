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

package me.piggypiglet.framework.file.framework;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Iterables;
import com.google.common.collect.Table;
import me.piggypiglet.framework.file.exceptions.config.ConfigSaveException;
import me.piggypiglet.framework.file.framework.objects.MapFileConfigurationSection;
import me.piggypiglet.framework.file.framework.objects.Mapper;
import me.piggypiglet.framework.file.implementations.BlankFileConfiguration;
import me.piggypiglet.framework.utils.map.Maps;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static me.piggypiglet.framework.utils.TypeUtils.valueNullDef;

public abstract class MapFileConfiguration implements FileConfiguration, MutableFileConfiguration {
    private static final Pattern PATH_REGEX = Pattern.compile("\\.");

    protected static final String NULL_STRING = "null";
    protected static final int NULL_NUM = 0;
    protected static final boolean NULL_BOOL = false;

    private final Predicate<String> match;
    protected final List<Mapper> mappers;

    private Map<String, Object> items;
    private Map<String, Object> flat;
    private Table<FileConfiguration, String, String> relocations = HashBasedTable.create();

    private File file;

    /**
     * Provision a predicate to see whether a file extension will match this configuration
     * @param match Predicate
     */
    protected MapFileConfiguration(@NotNull final Predicate<String> match) {
        this(match, new ArrayList<>());
    }

    protected MapFileConfiguration(@NotNull final Predicate<String> match, @NotNull final Mapper... mappers) {
        this(match, Arrays.asList(mappers));
    }

    protected MapFileConfiguration(@NotNull final Map<String, Object> items) {
        this.items = items;
        flatten();

        this.match = s -> true;
        this.mappers = new ArrayList<>();
    }

    private MapFileConfiguration(@NotNull final Predicate<String> match, @NotNull final List<Mapper> mappers) {
        this.match = match;
        this.mappers = mappers;
    }

    @NotNull
    protected abstract Map<String, Object> provide(@Nullable final File file, @NotNull final String content);

    @NotNull
    protected abstract String convert(@NotNull final Map<String, Object> items);

    /**
     * Load and populate an AbstractFileConfiguration instance
     * @param file File
     * @param content File's raw content
     * @return AbstractFileConfiguration
     */
    @NotNull
    public MapFileConfiguration load(@Nullable final File file, @NotNull final String content) {
        this.file = file;
        items = provide(file, content);
        mappers.forEach(f -> items = Maps.map(items, f.getClazz(), f.getFunction()));
        flatten();
        return this;
    }

    @NotNull
    public File getFile() {
        return file;
    }

    @NotNull
    public Predicate<String> getMatch() {
        return match;
    }

    @Override
    public Object get(@NotNull final String path) {
        if (relocations.containsColumn(path)) {
            return getFromRelocation(path, FileConfiguration::get);
        }

        return Maps.recursiveGet(items, path);
    }

    @Nullable
    @Override
    public Object get(@NotNull final String path, @NotNull final Object def) {
        return valueNullDef(get(path), null, def);
    }

    @SuppressWarnings("unchecked")
    @Override
    public FileConfiguration getConfigSection(String path) {
        return configSection((Map<String, Object>) get(path));
    }

    @Override
    public FileConfiguration getConfigSection(String path, FileConfiguration def) {
        return valueNullDef(getConfigSection(path), new BlankFileConfiguration(), def);
    }

    @Override
    public String getString(String path) {
        return (String) getFromRelocation(path, FileConfiguration::getString);
    }

    @Override
    public String getString(String path, String def) {
        return valueNullDef(getString(path), NULL_STRING, def);
    }

    @Override
    public Integer getInt(String path) {
        return number(path, Number::intValue);
    }

    @Override
    public int getInt(String path, int def) {
        return valueNullDef(getInt(path), NULL_NUM, def);
    }

    @Override
    public Long getLong(String path) {
        return number(path, Number::longValue);
    }

    @Override
    public long getLong(String path, long def) {
        return valueNullDef(getLong(path), (long) NULL_NUM, def);
    }

    @Override
    public Double getDouble(String path) {
        return number(path, Number::doubleValue);
    }

    @Override
    public double getDouble(String path, double def) {
        return valueNullDef(getDouble(path), (double) NULL_NUM, def);
    }

    private <T> T number(String path, Function<Number, T> value) {
        final Object obj = getFromRelocation(path, (config, _path) -> ((MapFileConfiguration) config).number(_path, value));
        return obj == null ? null : value.apply(((Number) obj));
    }

    @Override
    public Boolean getBoolean(String path) {
        return (Boolean) getFromRelocation(path, FileConfiguration::getBoolean);
    }

    @Override
    public boolean getBoolean(String path, boolean def) {
        return valueNullDef(getBoolean(path), NULL_BOOL, def);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> List<T> getList(String path) {
        return (List<T>) getFromRelocation(path, FileConfiguration::getList);
    }

    @Override
    public <T> List<T> getList(String path, List<T> def) {
        return valueNullDef(getList(path), new ArrayList<>(), def);
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

    @Override
    public List<FileConfiguration> getConfigList(String path, List<FileConfiguration> def) {
        return valueNullDef(getConfigList(path), new ArrayList<>(), def);
    }

    private FileConfiguration configSection(Map<String, Object> items) {
        return items == null ? null : new MapFileConfigurationSection(items);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void set(@NotNull final String path, @Nullable final Object value) {
        final String[] split = PATH_REGEX.split(path);

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

    public Map<String, Object> getAll() {
        return items;
    }

    public Map<String, Object> getFlattened() {
        return flat;
    }

    public void setRelocations(@NotNull final Table<FileConfiguration, String, String> relocations) {
        this.relocations = relocations;
    }

    private <T> T getFromRelocation(@NotNull final String path, @NotNull final BiFunction<FileConfiguration, String, T> getter) {
        if (relocations.containsColumn(path)) {
            final Map.Entry<FileConfiguration, String> entry = Iterables.getOnlyElement(relocations.column(path).entrySet());
            return getter.apply(entry.getKey(), entry.getValue());
        }

        return getter.apply(this, path);
    }

    private void flatten() {
        flat = items.entrySet().stream()
                .flatMap(Maps::flatten)
                .distinct()
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}