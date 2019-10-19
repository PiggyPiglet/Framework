package me.piggypiglet.framework.minecraft.player.inventory;

import java.util.HashMap;
import java.util.Map;

public final class Inventory {
    private final Map<Integer, String> armor = new HashMap<>(4);
    private final Map<Integer, String> items = new HashMap<>(27);
    private final Map<Integer, String> hotbar = new HashMap<>(9);
    private String offHand;

    public Map<Integer, String> getArmor() {
        return armor;
    }

    public Map<Integer, String> getItems() {
        return items;
    }

    public Map<Integer, String> getHotbar() {
        return hotbar;
    }

    public String getOffHand() {
        return offHand;
    }

    public void setOffHand(String offHand) {
        this.offHand = offHand;
    }
}
