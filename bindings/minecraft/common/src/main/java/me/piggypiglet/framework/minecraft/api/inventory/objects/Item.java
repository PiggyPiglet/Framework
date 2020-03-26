package me.piggypiglet.framework.minecraft.api.inventory.objects;

import me.piggypiglet.framework.minecraft.api.key.data.KeyNames;
import me.piggypiglet.framework.minecraft.api.key.framework.keyable.KeyEnum;
import me.piggypiglet.framework.minecraft.api.key.framework.keyable.Keyable;
import org.jetbrains.annotations.NotNull;

public abstract class Item<H> extends Keyable<Item.ItemKeys, H> {
    public Item() {
        super(ItemKeys.class, ItemKeys.UNKNOWN);
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
