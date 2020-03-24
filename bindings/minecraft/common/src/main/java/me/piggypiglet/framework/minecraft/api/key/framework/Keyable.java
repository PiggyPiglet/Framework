package me.piggypiglet.framework.minecraft.api.key.framework;

import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public interface Keyable {
    @NotNull
    <V> Optional<V> get(@NotNull final Key<V, ?> key);
}
