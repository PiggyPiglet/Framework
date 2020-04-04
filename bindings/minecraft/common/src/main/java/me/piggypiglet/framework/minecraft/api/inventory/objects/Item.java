package me.piggypiglet.framework.minecraft.api.inventory.objects;

import me.piggypiglet.framework.minecraft.api.key.data.KeyNames;
import me.piggypiglet.framework.minecraft.api.key.framework.keyable.KeyEnum;
import me.piggypiglet.framework.minecraft.api.key.framework.keyable.Keyable;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public abstract class Item<H> extends Keyable<H> {
    @SuppressWarnings({"unchecked", "rawtypes"})
    public Item(@NotNull final Function<H, Item<H>> initializer) {
        super(ItemKeys.class, ItemKeys.UNKNOWN, (Function) initializer);
    }

    protected enum ItemKeys implements KeyEnum {
        MATERIAL(KeyNames.ITEM_MATERIAL),
        AMOUNT(KeyNames.ITEM_AMOUNT),
        DURABILITY(KeyNames.ITEM_DURABILITY),
        LORE(KeyNames.ITEM_LORE),
        NAME(KeyNames.NAME),
        ENCHANTS(KeyNames.ITEM_ENCHANTS),
        UNBREAKABLE(KeyNames.ITEM_UNBREAKABLE),
        FLAGS(KeyNames.ITEM_FLAGS),
        UNKNOWN(KeyNames.UNKNOWN);

        private final KeyNames parent;

        ItemKeys(@NotNull final KeyNames parent) {
            this.parent = parent;
        }

        @NotNull
        @Override
        public KeyNames getParent() {
            return parent;
        }
    }
}
