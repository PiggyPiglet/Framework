package me.piggypiglet.framework.bukkit.binding.inventory.item;

import me.piggypiglet.framework.minecraft.api.inventory.item.framework.Item;
import me.piggypiglet.framework.minecraft.api.inventory.item.framework.MutableItem;
import me.piggypiglet.framework.minecraft.api.inventory.item.material.Material;
import me.piggypiglet.framework.minecraft.api.key.data.KeyFactory;
import me.piggypiglet.framework.minecraft.api.key.data.KeyNames;
import me.piggypiglet.framework.minecraft.api.key.framework.KeyImpl;
import me.piggypiglet.framework.minecraft.api.key.framework.keyable.KeyGroup;
import me.piggypiglet.framework.utils.map.Maps;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public final class BukkitItem extends Item<ItemStack> implements MutableItem {
    private final ItemStack handle;

    public BukkitItem(@NotNull final ItemStack handle) {
        super(BukkitItem::new);
        this.handle = handle;
    }

    @NotNull
    @Override
    protected Map<KeyGroup, KeyImpl<?, ItemStack>> provideKeyFunctions() {
        return Maps.of(new HashMap<KeyGroup, KeyImpl<?, ItemStack>>())
                .key(ItemKeys.MATERIAL).value(KeyFactory.ofNullable(item -> Material.fromName(item.getType().name()), KeyNames.ITEM_MATERIAL))
                .key(ItemKeys.AMOUNT).value(KeyFactory.ofNullable(ItemStack::getAmount, KeyNames.ITEM_AMOUNT))
                .build();
    }

    @NotNull
    @Override
    public ItemStack getHandle() {
        return handle;
    }
}
