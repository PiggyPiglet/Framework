package me.piggypiglet.framework.file.framework.implementations.map;

import me.piggypiglet.framework.file.framework.AbstractFileConfiguration;
import me.piggypiglet.framework.file.framework.FileConfiguration;
import me.piggypiglet.framework.utils.map.Maps;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class MapFileConfiguration extends AbstractFileConfiguration {
    private Map<String, Object> items;
    private Map<String, Object> flat;

    /**
     * Provision a predicate to see whether a file extension will match this configuration
     *
     * @param match Predicate
     */
    protected MapFileConfiguration(Predicate<String> match) {
        super(match);
    }

    protected abstract Map<String, Object> provide(File file, String fileContent);

    @Override
    protected void internalLoad(File file, String fileContent) {
        items = provide(file, fileContent);
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
        return ((Number) flat.get(path)).intValue();
    }

    @Override
    public Long getLong(String path) {
        return ((Number) flat.get(path)).longValue();
    }

    @Override
    public Double getDouble(String path) {
        return ((Number) flat.get(path)).doubleValue();
    }

    @Override
    public Boolean getBoolean(String path) {
        return (Boolean) flat.get(path);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<String> getStringList(String path) {
        return (List<String>) getList(path);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<FileConfiguration> getConfigList(String path) {
        final List<Map<String, Object>> list = (List<Map<String, Object>>) getList(path);

        if (list.size() > 0) {
            return list.stream()
                    .map(this::configSection)
                    .collect(Collectors.toList());
        }

        return null;
    }

    @Override
    public List<?> getList(String path) {
        return (List<?>) flat.get(path);
    }

    @Override
    protected Map<String, Object> retrieveAll() {
        return items;
    }

    private FileConfiguration configSection(Map<String, Object> items) {
        return new MapFileConfigurationSection(items);
    }
}
