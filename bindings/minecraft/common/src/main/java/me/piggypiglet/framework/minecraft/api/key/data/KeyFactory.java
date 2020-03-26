package me.piggypiglet.framework.minecraft.api.key.data;

import me.piggypiglet.framework.minecraft.api.key.framework.Key;
import me.piggypiglet.framework.minecraft.api.key.framework.KeyImpl;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.function.Function;

public final class KeyFactory {
    private KeyFactory() {}

    @NotNull
    public static  <V, H> KeyImpl<V, H> ofNullable(@NotNull final Function<H, V> getter, @NotNull final KeyNames name) {
        return initImpl(handle -> Optional.ofNullable(getter.apply(handle)), name);
    }

    @NotNull
    public static <V, H> KeyImpl<V, H> of(@NotNull final Function<H, Optional<V>> getter, @NotNull final KeyNames name) {
        return initImpl(handle -> {
            final Optional<V> result = getter.apply(handle);

            //noinspection OptionalAssignedToNull
            if (result == null) {
                return Optional.empty();
            }

            return result;
        }, name);
    }

    private static <V, H> KeyImpl<V, H> initImpl(@NotNull final Function<H, Optional<V>> getter, @NotNull final KeyNames name) {
        return new KeyImpl<V, H>() {
            @NotNull
            @Override
            public Key<V, H> getKey() {
                return () -> name;
            }

            @NotNull
            @Override
            public Optional<V> get(@NotNull final H handle) {
                return getter.apply(handle);
            }
        };
    }
}
