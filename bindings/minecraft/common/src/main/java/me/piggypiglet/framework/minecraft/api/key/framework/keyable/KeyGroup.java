package me.piggypiglet.framework.minecraft.api.key.framework.keyable;

import me.piggypiglet.framework.minecraft.api.key.data.KeyNames;
import org.jetbrains.annotations.NotNull;

public interface KeyGroup {
    @NotNull
    KeyNames getParent();

    @NotNull
    String getName();
}
