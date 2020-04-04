package me.piggypiglet.framework.minecraft.api.inventory.framework;

import me.piggypiglet.framework.minecraft.api.inventory.objects.Item;
import me.piggypiglet.framework.minecraft.api.key.data.KeyNames;
import me.piggypiglet.framework.minecraft.api.key.framework.keyable.KeyEnum;
import me.piggypiglet.framework.minecraft.api.key.framework.keyable.Keyable;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.function.Function;

public abstract class Inventory<H> extends Keyable<H> {
    @SuppressWarnings({"unchecked", "rawtypes"})
    protected Inventory(@NotNull final Function<H, Inventory<H>> initializer) {
        super(InventoryKeys.class, InventoryKeys.UNKNOWN, (Function) initializer);
    }

    @NotNull
    public abstract Map<Integer, Item<?>> getAll();

    protected abstract void handleUpdate(final int slot, @NotNull final Item<?> item);

    @NotNull
    public Item<?> get(final int slot) {
        return getAll().get(slot);
    }

    protected enum InventoryKeys implements KeyEnum {
        NAME(KeyNames.NAME),
        UNKNOWN(KeyNames.UNKNOWN);

        private final KeyNames parent;

        InventoryKeys(@NotNull final KeyNames parent) {
            this.parent = parent;
        }

        @NotNull
        @Override
        public KeyNames getParent() {
            return parent;
        }
    }
}
