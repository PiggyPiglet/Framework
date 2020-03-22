package me.piggypiglet.framework.minecraft.api.meta;

import me.piggypiglet.framework.minecraft.api.meta.data.KeyNames;
import me.piggypiglet.framework.minecraft.api.meta.framework.Key;

import java.util.UUID;

public final class Keys {
    // PLAYER
    public static final Key<UUID, ?> UUID = () -> KeyNames.UUID;
}
