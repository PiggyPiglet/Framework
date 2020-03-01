package me.piggypiglet.framework.addon.framework.api;

import me.piggypiglet.framework.init.builder.stages.addons.AddonsBuilder;
import me.piggypiglet.framework.utils.builder.AbstractBuilder;
import me.piggypiglet.framework.utils.builder.GenericBuilder;
import me.piggypiglet.framework.utils.map.Maps;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@SuppressWarnings("unchecked")
//todo: somehow carry the main class type into here, for a FrameworkBuilder return type.
public abstract class AddonConfigurationBuilder<T extends AddonConfigurationBuilder<T>> extends AbstractBuilder<AddonConfiguration, AddonsBuilder> {
    private final T instance = (T) this;

    protected final Map<String, FileConfigInfo> fileConfigs = new HashMap<>();

    @NotNull
    public Maps.Builder<String, String, String, T> mappings(@NotNull final String config) {
        return Maps.builder(new HashMap<>(), map -> {
            put(config, info -> info.setMappings(map));
            return instance;
        });
    }

    @NotNull
    public T mappings(@NotNull final String config, @NotNull final Map<String, String> mappings) {
        put(config, info -> info.setMappings(mappings));
        return instance;
    }

    @NotNull
    public Maps.Builder<String, String, String, T> values(@NotNull final String config) {
        return Maps.builder(new HashMap<>(), map -> {
            put(config, info -> info.setValues(map));
            return instance;
        });
    }

    @NotNull
    public T values(@NotNull final String config, @NotNull final Map<String, String> values) {
        put(config, info -> info.setValues(values));
        return instance;
    }

    private void put(@NotNull final String config, @NotNull final Consumer<FileConfigInfo> function) {
        fileConfigs.putIfAbsent(config, GenericBuilder.of(() -> new FileConfigInfo(config))
                .with(function)
                .build());
    }

    @Override
    protected AddonConfiguration provideBuild() {
        return new AddonConfiguration(fileConfigs);
    }
}
