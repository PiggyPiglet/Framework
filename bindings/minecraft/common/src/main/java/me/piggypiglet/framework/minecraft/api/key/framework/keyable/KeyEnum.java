package me.piggypiglet.framework.minecraft.api.key.framework.keyable;

import me.piggypiglet.framework.minecraft.api.key.data.KeyNames;
import me.piggypiglet.framework.minecraft.api.key.framework.Key;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public interface KeyEnum {
    @NotNull
    KeyNames getParent();

    @NotNull
    static <T extends Enum<T> & KeyEnum> Optional<T> fromKey(@NotNull final Class<T> enumClass, @NotNull final Key<?, ?> key) {
        T value = null;

        for (final T name : enumClass.getEnumConstants()) {
            if (name.getParent() == key.getName()) value = name;
        }

        return Optional.ofNullable(value);
    }
}
