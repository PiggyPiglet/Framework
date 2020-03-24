package me.piggypiglet.framework.minecraft.api.inventory;

import me.piggypiglet.framework.minecraft.api.inventory.framework.Inventory;
import me.piggypiglet.framework.minecraft.api.inventory.framework.MutableInventory;
import me.piggypiglet.framework.minecraft.api.inventory.implementations.InventoryImpl;
import me.piggypiglet.framework.minecraft.api.inventory.implementations.MutableInventoryImpl;
import me.piggypiglet.framework.minecraft.api.inventory.objects.Item;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public final class Inventories {
    public static Inventory of(@NotNull final Map<Integer, Item> items) {
        return new InventoryImpl(items);
    }

    public static MutableInventory ofMutable(@NotNull final Map<Integer, Item> items) {
        return new MutableInventoryImpl(items);
    }
}
