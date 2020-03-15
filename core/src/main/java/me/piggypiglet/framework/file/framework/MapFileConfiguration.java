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
    private Table<MapFileConfiguration, String, String> relocations = HashBasedTable.create();

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

    @Nullable
    @Override
    public Object get(@NotNull final String path) {
        if (relocations.containsColumn(path)) {
            return getFromRelocation(path, FileConfiguration::get,
                    (cfg, _path) -> Maps.recursiveGet(cfg.items, _path));
        }

        return Maps.recursiveGet(items, path);
    }

    @NotNull
    @Override
    public Object get(@NotNull final String path, @NotNull final Object def) {
        return valueNullDef(get(path), null, def);
    }

    @SuppressWarnings("unchecked")
    @Override
    public FileConfiguration getConfigSection(@NotNull final String path) {
        return configSection((Map<String, Object>) get(path));
    }

    @NotNull
    @Override
    public FileConfiguration getConfigSection(@NotNull final String path, @NotNull final FileConfiguration def) {
        return valueNullDef(getConfigSection(path), null, def);
    }

    @Nullable
    @Override
    public String getString(@NotNull final String path) {
        return getFromRelocation(path, FileConfiguration::getString, this::getFromFlat);
    }

    @NotNull
    @Override
    public String getString(@NotNull final String path, @NotNull final String def) {
        return valueNullDef(getString(path), NULL_STRING, def);
    }

    @Nullable
    @Override
    public Integer getInt(@NotNull final String path) {
        return number(path, Number::intValue);
    }

    @Override
    public int getInt(@NotNull final String path, final int def) {
        return valueNullDef(getInt(path), NULL_NUM, def);
    }

    @Nullable
    @Override
    public Long getLong(@NotNull final String path) {
        return number(path, Number::longValue);
    }

    @Override
    public long getLong(@NotNull final String path, final long def) {
        return valueNullDef(getLong(path), (long) NULL_NUM, def);
    }

    @Nullable
    @Override
    public Double getDouble(@NotNull final String path) {
        return number(path, Number::doubleValue);
    }

    @Override
    public double getDouble(@NotNull final String path, final double def) {
        return valueNullDef(getDouble(path), (double) NULL_NUM, def);
    }

    @Nullable
    private <T> T number(@NotNull final String path, @NotNull final Function<Number, T> value) {
        return Optional.ofNullable(getFromRelocation(path, (cfg, _path) -> cfg.number(_path, value), this::getFromFlat))
                .map(obj -> (Number) obj)
                .map(value)
                .orElse(null);
    }

    @Nullable
    @Override
    public Boolean getBoolean(@NotNull final String path) {
        return getFromRelocation(path, FileConfiguration::getBoolean, this::getFromFlat);
    }

    @Override
    public boolean getBoolean(@NotNull final String path, final boolean def) {
        return valueNullDef(getBoolean(path), NULL_BOOL, def);
    }

    @SuppressWarnings("unchecked")
    @Nullable
    @Override
    public <T> List<T> getList(@NotNull final String path) {
        return (List<T>) getFromRelocation(path, FileConfiguration::getList, MapFileConfiguration::get);
    }

    @NotNull
    @Override
    public <T> List<T> getList(@NotNull final String path, @NotNull final List<T> def) {
        return valueNullDef(getList(path), null, def);
    }

    @NotNull
    @Override
    public List<FileConfiguration> getConfigList(@NotNull final String path) {
        final List<Map<String, Object>> list = getList(path);

        if (list != null && !list.isEmpty()) {
            return list.stream()
                    .map(this::configSection)
                    .collect(Collectors.toList());
        }

        return new ArrayList<>();
    }

    @NotNull
    @Override
    public List<FileConfiguration> getConfigList(@NotNull final String path, @NotNull final List<FileConfiguration> def) {
        return valueNullDef(getConfigList(path), new ArrayList<>(), def);
    }

    @Nullable
    private FileConfiguration configSection(@Nullable final Map<String, Object> items) {
        return items == null ? new BlankFileConfiguration() : new MapFileConfigurationSection(items).load(null, "");
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

    @NotNull
    public Map<String, Object> getAll() {
        return items;
    }

    @NotNull
    public Map<String, Object> getFlattened() {
        return flat;
    }

    public void setRelocations(@NotNull final Table<MapFileConfiguration, String, String> relocations) {
        this.relocations = relocations;
    }

    @SuppressWarnings("unchecked")
    private <T> T getFromFlat(@NotNull final MapFileConfiguration config, @NotNull final String path) {
        return (T) config.flat.get(path);
    }

    private <T> T getFromRelocation(@NotNull final String path, @NotNull final BiFunction<MapFileConfiguration, String, T> retrier,
                                    @NotNull final BiFunction<MapFileConfiguration, String, T> getter) {
        if (relocations.containsColumn(path)) {
            final Map.Entry<MapFileConfiguration, String> entry = Iterables.getOnlyElement(relocations.column(path).entrySet());
            return retrier.apply(entry.getKey(), entry.getValue());
        }

        return getter.apply(this, path);
    }

    private void flatten() {
        flat = items.entrySet().stream()
                .flatMap(Maps::flatten)
                .distinct()
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public String toString() {
        return String.valueOf(items);
    }
}