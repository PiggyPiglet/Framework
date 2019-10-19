package me.piggypiglet.framework.minecraft.player.inventory;

import java.util.List;
import java.util.Map;

public final class Item {
    private final String type;
    private final int amount;
    private final short durability;
    private final int getMaxStackSize;
    private final List<String> lore;
    private final String displayName;
    private final List<String> enchants;
    private final boolean unbreakable;
    private final List<String> itemFlags;
    private final Map<String, Integer> attributes;

    public Item(String type, int amount, short durability,
                int getMaxStackSize, List<String> lore, String displayName,
                List<String> enchants, boolean unbreakable, List<String> itemFlags,
                Map<String, Integer> attributes) {
        this.type = type;
        this.amount = amount;
        this.durability = durability;
        this.getMaxStackSize = getMaxStackSize;
        this.lore = lore;
        this.displayName = displayName;
        this.enchants = enchants;
        this.unbreakable = unbreakable;
        this.itemFlags = itemFlags;
        this.attributes = attributes;
    }

    public String getType() {
        return type;
    }

    public int getAmount() {
        return amount;
    }

    public short getDurability() {
        return durability;
    }

    public int getGetMaxStackSize() {
        return getMaxStackSize;
    }

    public List<String> getLore() {
        return lore;
    }

    public String getDisplayName() {
        return displayName;
    }

    public List<String> getEnchants() {
        return enchants;
    }

    public boolean isUnbreakable() {
        return unbreakable;
    }

    public List<String> getItemFlags() {
        return itemFlags;
    }

    public Map<String, Integer> getAttributes() {
        return attributes;
    }
}
