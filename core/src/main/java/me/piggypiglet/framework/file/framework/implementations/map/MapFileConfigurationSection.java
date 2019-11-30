package me.piggypiglet.framework.file.framework.implementations.map;

import me.piggypiglet.framework.utils.annotations.reflection.Disabled;

import java.io.File;
import java.util.Map;

@Disabled
public final class MapFileConfigurationSection extends MapFileConfiguration {
    private final Map<String, Object> items;

    public MapFileConfigurationSection(Map<String, Object> items) {
        super(null);
        this.items = items;
    }

    @Override
    protected Map<String, Object> provide(File file, String fileContent) {
        return items;
    }
}
