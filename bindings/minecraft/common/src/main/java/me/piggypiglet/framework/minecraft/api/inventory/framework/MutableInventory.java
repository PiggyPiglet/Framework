package me.piggypiglet.framework.minecraft.api.inventory.framework;

import me.piggypiglet.framework.minecraft.api.inventory.item.framework.Item;
import org.jetbrains.annotations.NotNull;

public interface MutableInventory {
    default void set(final int slot, @NotNull final Item<?> item) {
        ((Inventory<?>) this).handleUpdate(slot, item);
    }
}
