package me.piggypiglet.framework.minecraft.api.inventory.framework.player;

import me.piggypiglet.framework.minecraft.api.inventory.framework.Inventory;
import me.piggypiglet.framework.minecraft.api.inventory.item.framework.Item;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.function.Function;

public abstract class PlayerInventory<H> extends Inventory<H> {
    private static final int ARMOR_START = 100;
    private static final int ARMOR_END = 103;
    private static final int HOTBAR_START = 0;
    private static final int HOTBAR_END = 8;
    private static final int MAIN_START = 9;
    private static final int MAIN_END = 35;

    protected PlayerInventory(@NotNull final Function<H, Inventory<H>> initializer) {
        super(initializer);
    }

    @NotNull
    public Optional<Item<?>>[] getArmor() {
        return get(ARMOR_START, ARMOR_END);
    }

    @NotNull
    public Optional<Item<?>>[] getHotbar() {
        return get(HOTBAR_START, HOTBAR_END);
    }

    @NotNull
    public Optional<Item<?>>[] getMain() {
        return get(MAIN_START, MAIN_END);
    }

    @SuppressWarnings("unchecked")
    @NotNull
    private Optional<Item<?>>[] get(final int startInclusive, final int endInclusive) {
        final Optional<Item<?>>[] inven = getAll();
        final Optional<Item<?>>[] result = new Optional[endInclusive - startInclusive + 1];

        int j = 0;
        for (int i = startInclusive; i <= endInclusive; ++i) {
            result[j++] = inven[i];
        }

        return result;
    }
}
