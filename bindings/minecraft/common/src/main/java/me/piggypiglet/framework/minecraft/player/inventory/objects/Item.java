package me.piggypiglet.framework.minecraft.player.inventory.objects;

import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("unused")
public class Item {
    private String type;
    private int amount;
    private short durability;
    private int getMaxStackSize;
    private List<String> lore;
    private String displayName;
    private Map<String, Integer> enchants;
    private boolean unbreakable;
    private Set<String> itemFlags;

    public Item(String type, int amount, short durability,
                int getMaxStackSize, List<String> lore, String displayName,
                Map<String, Integer> enchants, boolean unbreakable, Set<String> itemFlags) {
        this.type = type;
        this.amount = amount;
        this.durability = durability;
        this.getMaxStackSize = getMaxStackSize;
        this.lore = lore;
        this.displayName = displayName;
        this.enchants = enchants;
        this.unbreakable = unbreakable;
        this.itemFlags = itemFlags;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public short getDurability() {
        return durability;
    }

    public void setDurability(short durability) {
        this.durability = durability;
    }

    public int getGetMaxStackSize() {
        return getMaxStackSize;
    }

    public void setGetMaxStackSize(int getMaxStackSize) {
        this.getMaxStackSize = getMaxStackSize;
    }

    public List<String> getLore() {
        return lore;
    }

    public void setLore(List<String> lore) {
        this.lore = lore;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Map<String, Integer> getEnchants() {
        return enchants;
    }

    public void setEnchants(Map<String, Integer> enchants) {
        this.enchants = enchants;
    }

    public boolean isUnbreakable() {
        return unbreakable;
    }

    public void setUnbreakable(boolean unbreakable) {
        this.unbreakable = unbreakable;
    }

    public Set<String> getItemFlags() {
        return itemFlags;
    }

    public void setItemFlags(Set<String> itemFlags) {
        this.itemFlags = itemFlags;
    }
}
