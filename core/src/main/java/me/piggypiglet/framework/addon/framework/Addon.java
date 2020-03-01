package me.piggypiglet.framework.addon.framework;

import me.piggypiglet.framework.addon.framework.api.AddonConfigurationBuilder;
import me.piggypiglet.framework.addon.init.AddonData;
import org.jetbrains.annotations.NotNull;

public abstract class Addon<T extends Addon<T>> extends AddonConfigurationBuilder<T> {
    @NotNull
    protected abstract AddonData provideConfig();

    @NotNull
    public AddonData getConfig() {
        return provideConfig();
    }
}
