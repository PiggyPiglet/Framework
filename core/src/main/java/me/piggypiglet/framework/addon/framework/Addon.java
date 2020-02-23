package me.piggypiglet.framework.addon.framework;

import me.piggypiglet.framework.addon.builders.AddonBuilder;
import me.piggypiglet.framework.addon.builders.AddonData;
import me.piggypiglet.framework.addon.framework.config.AddonConfigurationBuilder;
import org.jetbrains.annotations.NotNull;

public abstract class Addon<T extends Addon<T>> extends AddonConfigurationBuilder<T> {
    @NotNull
    protected abstract AddonData provideConfig(@NotNull final AddonBuilder<AddonData> builder);

    @NotNull
    public AddonData getConfig() {
        return provideConfig(new AddonBuilder<>(this));
    }
}
