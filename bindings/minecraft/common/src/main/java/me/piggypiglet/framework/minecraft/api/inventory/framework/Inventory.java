package me.piggypiglet.framework.minecraft.api.inventory.framework;

import me.piggypiglet.framework.minecraft.api.inventory.item.framework.Item;
import me.piggypiglet.framework.minecraft.api.key.data.KeyNames;
import me.piggypiglet.framework.minecraft.api.key.framework.keyable.KeyGroup;
import me.piggypiglet.framework.minecraft.api.key.framework.keyable.Keyable;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.function.Function;

public abstract class Inventory<H> extends Keyable<H> {
    @SuppressWarnings({"unchecked", "rawtypes"})
    protected Inventory(@NotNull final Function<H, Inventory<H>> initializer) {
        super(InventoryKeys.values(), InventoryKeys.UNKNOWN, (Function) initializer);
    }

    @NotNull
    public abstract Optional<Item<?>>[] getAll();

    protected void handleUpdate(final int slot, @NotNull final Item<?> item) {
        throw new UnsupportedOperationException("This inventory isn't mutable.");
    }

    @NotNull
    public Optional<Item<?>> get(final int slot) {
        return getAll()[slot];
    }

    protected enum InventoryKeys implements KeyGroup {
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

        @NotNull
        @Override
        public String getName() {
            return name();
        }
    }
}
