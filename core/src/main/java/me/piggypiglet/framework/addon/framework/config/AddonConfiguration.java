package me.piggypiglet.framework.addon.framework.config;

import me.piggypiglet.framework.addon.framework.config.objects.ConfigMappings;
import org.jetbrains.annotations.NotNull;

public class AddonConfiguration {
    private final ConfigMappings mappings;

    public AddonConfiguration(@NotNull final ConfigMappings mappings) {
        this.mappings = mappings;
    }

    @NotNull
    public ConfigMappings getMappings() {
        return mappings;
    }
}
