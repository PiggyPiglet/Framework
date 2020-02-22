package me.piggypiglet.framework.addon.framework.config;

import me.piggypiglet.framework.addon.framework.config.objects.ConfigMappings;
import me.piggypiglet.framework.utils.builder.AbstractBuilder;
import me.piggypiglet.framework.utils.map.Maps;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unchecked")
public abstract class AddonConfigurationBuilder<T extends AddonConfigurationBuilder<T, P, R>, P extends AddonConfiguration, R> extends AbstractBuilder<P, R> {
    private final T instance = (T) this;

    protected ConfigMappings configMappings = null;

    @NotNull
    public Maps.Builder<String, String, String, T> mappings(@NotNull final String config) {
        return Maps.builder(new HashMap<>(), map -> {
            configMappings = new ConfigMappings(config, map);
            return instance;
        });
    }

    @NotNull
    public T mappings(@NotNull final String config, @NotNull final Map<String, String> locations) {
        configMappings = new ConfigMappings(config, locations);
        return instance;
    }
}
