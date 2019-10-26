package me.piggypiglet.framework.minecraft.player.inventory.objects;

import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
public class Item {
    private String type;
    private int amount;
    private short durability;
    private int getMaxStackSize;
    private List<String> lore;
    private String displayName;
    private List<String> enchants;
    private boolean unbreakable;
    private List<String> itemFlags;
    private Map<String, Integer> attributes;

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

    public List<String> getEnchants() {
        return enchants;
    }

    public void setEnchants(List<String> enchants) {
        this.enchants = enchants;
    }

    public boolean isUnbreakable() {
        return unbreakable;
    }

    public void setUnbreakable(boolean unbreakable) {
        this.unbreakable = unbreakable;
    }

    public List<String> getItemFlags() {
        return itemFlags;
    }

    public void setItemFlags(List<String> itemFlags) {
        this.itemFlags = itemFlags;
    }

    public Map<String, Integer> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Integer> attributes) {
        this.attributes = attributes;
    }
}
