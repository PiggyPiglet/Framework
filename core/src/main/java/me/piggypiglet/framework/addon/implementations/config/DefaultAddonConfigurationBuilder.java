package me.piggypiglet.framework.addon.implementations.config;

import me.piggypiglet.framework.addon.framework.config.AddonConfiguration;
import me.piggypiglet.framework.addon.framework.config.AddonConfigurationBuilder;
import org.jetbrains.annotations.NotNull;

public final class DefaultAddonConfigurationBuilder<R> extends AddonConfigurationBuilder<DefaultAddonConfigurationBuilder<R>, AddonConfiguration, R> {
    @NotNull
    @Override
    protected AddonConfiguration provideBuild() {
        return new AddonConfiguration(configMappings);
    }
}
