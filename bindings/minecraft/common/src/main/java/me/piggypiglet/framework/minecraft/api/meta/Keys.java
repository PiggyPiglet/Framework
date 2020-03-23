package me.piggypiglet.framework.minecraft.api.meta;

import me.piggypiglet.framework.minecraft.api.meta.data.KeyNames;
import me.piggypiglet.framework.minecraft.api.meta.framework.Key;

import java.net.InetSocketAddress;
import java.util.UUID;

public final class Keys {
    public static final Key<UUID, ?> UUID = () -> KeyNames.UUID;
    public static final Key<String, ?> NAME = () -> KeyNames.NAME;
    public static final Key<Double, ?> HEALTH = () -> KeyNames.HEALTH;
    public static final Key<Double, ?> HEALTH_SCALE = () -> KeyNames.HEALTH_SCALE;
    public static final Key<InetSocketAddress, ?> PLAYER_ADDRESS = () -> KeyNames.PLAYER_ADDRESS;
}
