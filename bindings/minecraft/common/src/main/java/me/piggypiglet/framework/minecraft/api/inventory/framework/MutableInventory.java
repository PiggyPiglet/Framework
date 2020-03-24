package me.piggypiglet.framework.minecraft.api.inventory.framework;

import me.piggypiglet.framework.minecraft.api.inventory.objects.Item;
import org.jetbrains.annotations.NotNull;

public interface MutableInventory extends Inventory {
    void set(final int slot, @NotNull final Item item);
}
