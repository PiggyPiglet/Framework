package me.piggypiglet.framework.minecraft.api.meta.framework;

import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public interface KeyImpl<V, H> {
    @NotNull
    Key<V, H> getKey();

    @NotNull
    Optional<V> get(@NotNull final H handle);
}
