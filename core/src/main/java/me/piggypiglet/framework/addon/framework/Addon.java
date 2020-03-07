package me.piggypiglet.framework.addon.framework;

import me.piggypiglet.framework.addon.framework.config.AddonConfigurationBuilder;
import me.piggypiglet.framework.addon.framework.config.DefaultAddonConfigurationBuilder;
import me.piggypiglet.framework.addon.init.AddonBuilder;
import me.piggypiglet.framework.addon.init.AddonData;
import org.jetbrains.annotations.NotNull;

public abstract class Addon {
    @NotNull
    protected abstract AddonData provideConfig(@NotNull final AddonBuilder<AddonData> builder);

    @SuppressWarnings("unchecked")
    @NotNull
    protected <T extends AddonConfigurationBuilder<T>> T provideBuilder() {
        return (T) new DefaultAddonConfigurationBuilder();
    }

    @NotNull
    public AddonData getConfig() {
        return provideConfig(new AddonBuilder<>(this));
    }

    @NotNull
    public <T extends AddonConfigurationBuilder<T>> T getBuilder() {
        return provideBuilder();
    }
}
