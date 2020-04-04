package me.piggypiglet.framework.minecraft.api.inventory.framework.player;

import me.piggypiglet.framework.minecraft.api.inventory.framework.Inventory;
import me.piggypiglet.framework.minecraft.api.inventory.objects.Item;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public abstract class PlayerInventory<H> extends Inventory<H> {
    protected PlayerInventory(@NotNull final Function<H, Inventory<H>> initializer) {
        super(initializer);
    }

    @NotNull
    public Map<Integer, Item<?>> getArmor() {
        return get(100, 103);
    }

    @NotNull
    public Map<Integer, Item<?>> getHotbar() {
        return get(0, 8);
    }

    @NotNull
    public Map<Integer, Item<?>> getMain() {
        return get(9, 35);
    }

    @NotNull
    private Map<Integer, Item<?>> get(final int startInclusive, final int endInclusive) {
        final Map<Integer, Item<?>> inven = getAll();
        final Map<Integer, Item<?>> result = new HashMap<>();

        int j = 0;
        for (int i = startInclusive; i <= endInclusive; ++i) {
            result.put(j++, inven.get(i));
        }

        return result;
    }

//    protected enum
}
