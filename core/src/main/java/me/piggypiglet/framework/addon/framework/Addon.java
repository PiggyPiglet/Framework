package me.piggypiglet.framework.addon.framework;

import me.piggypiglet.framework.addon.builders.AddonBuilder;
import me.piggypiglet.framework.addon.builders.AddonData;
import org.jetbrains.annotations.NotNull;

public interface Addon {
    @NotNull
    AddonData provideConfig(@NotNull final AddonBuilder<AddonData> builder);
}
