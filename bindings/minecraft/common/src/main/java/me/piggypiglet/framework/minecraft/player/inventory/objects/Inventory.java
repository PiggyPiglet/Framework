package me.piggypiglet.framework.minecraft.player.inventory.objects;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Inventory {
    private final UUID uuid;
    private final Map<Integer, Item> armor = new HashMap<>(4);
    private final Map<Integer, Item> items = new HashMap<>(27);
    private final Map<Integer, Item> hotbar = new HashMap<>(9);
    private Item offHand;

    public Inventory(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getOwner() {
        return uuid;
    }

    public Map<Integer, Item> getArmor() {
        return armor;
    }

    public Map<Integer, Item> getItems() {
        return items;
    }

    public Map<Integer, Item> getHotbar() {
        return hotbar;
    }

    public Item getOffHand() {
        return offHand;
    }

    public void setOffHand(Item offHand) {
        this.offHand = offHand;
    }
}
