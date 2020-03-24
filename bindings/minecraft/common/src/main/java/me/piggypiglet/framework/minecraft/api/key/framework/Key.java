package me.piggypiglet.framework.minecraft.api.key.framework;

import com.google.inject.TypeLiteral;
import me.piggypiglet.framework.minecraft.api.key.data.KeyNames;
import org.jetbrains.annotations.NotNull;


public interface Key<V, H> {
    @NotNull
    KeyNames getName();

    @NotNull
    default TypeLiteral<V> getValue() {
        return new TypeLiteral<V>(){};
    }

    @NotNull
    default TypeLiteral<H> getHandle() {
        return new TypeLiteral<H>(){};
    }
}
