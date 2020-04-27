package me.piggypiglet.framework.minecraft.api.key;

import me.piggypiglet.framework.minecraft.api.inventory.item.material.Material;
import me.piggypiglet.framework.minecraft.api.key.data.KeyFactory;
import me.piggypiglet.framework.minecraft.api.key.data.KeyNames;
import me.piggypiglet.framework.minecraft.api.key.framework.Key;
import me.piggypiglet.framework.minecraft.api.key.framework.KeyImpl;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public final class Keys {
    private Keys() {
        throw new AssertionError("This class cannot be initialized.");
    }

    public static final KeyImpl<?, ?> UNKNOWN = KeyFactory.ofNullable(redundant -> null, KeyNames.UNKNOWN);

    public static final Key<UUID, ?> UUID = () -> KeyNames.UUID;
    public static final Key<String, ?> NAME = () -> KeyNames.NAME;
    public static final Key<Double, ?> ENTITY_HEALTH = () -> KeyNames.ENTITY_HEALTH;
    public static final Key<Double, ?> ENTITY_HEALTH_SCALE = () -> KeyNames.ENTITY_HEALTH_SCALE;
    public static final Key<InetSocketAddress, ?> PLAYER_ADDRESS = () -> KeyNames.PLAYER_ADDRESS;

    public static final Key<Material, ?> ITEM_MATERIAL = () -> KeyNames.ITEM_MATERIAL;
    public static final Key<Integer, ?> ITEM_AMOUNT = () -> KeyNames.ITEM_AMOUNT;
    public static final Key<Integer, ?> ITEM_DURABILITY = () -> KeyNames.ITEM_DURABILITY;
    public static final Key<List<String>, ?> ITEM_LORE = () -> KeyNames.ITEM_LORE;
    public static final Key<Map<String, Integer>, ?> ITEM_ENCHANTS = () -> KeyNames.ITEM_ENCHANTS;
    public static final Key<Boolean, ?> ITEM_UNBREAKABLE = () -> KeyNames.ITEM_UNBREAKABLE;
    public static final Key<Set<String>, ?> ITEM_FLAGS = () -> KeyNames.ITEM_FLAGS;
}
