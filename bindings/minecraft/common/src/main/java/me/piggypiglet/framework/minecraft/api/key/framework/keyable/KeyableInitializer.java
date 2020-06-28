package me.piggypiglet.framework.minecraft.api.key.framework.keyable;

import org.jetbrains.annotations.NotNull;

public interface KeyableInitializer<T extends Keyable<?>> {
    @NotNull
    T create();
}
