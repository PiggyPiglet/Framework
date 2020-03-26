package me.piggypiglet.framework.minecraft.api.inventory.framework;

import me.piggypiglet.framework.minecraft.api.inventory.objects.Item;
import me.piggypiglet.framework.minecraft.api.key.framework.keyable.Keyable;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public interface Inventory extends Keyable {
    @NotNull
    Map<Integer, Item> getAll();

    @NotNull
    default Item get(final int slot) {
        return getAll().get(slot);
    }
}
