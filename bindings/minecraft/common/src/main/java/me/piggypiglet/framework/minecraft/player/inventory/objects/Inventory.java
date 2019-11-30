/*
 * MIT License
 *
 * Copyright (c) 2019 PiggyPiglet
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package me.piggypiglet.framework.minecraft.player.inventory.objects;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Inventory {
    private final UUID uuid;
    private final Map<Integer, Item> crafting = new HashMap<>(5);
    private final Map<Integer, Item> armor = new HashMap<>(4);
    private final Map<Integer, Item> items = new HashMap<>(27);
    private final Map<Integer, Item> hotbar = new HashMap<>(9);
    private Item hand;
    private Item offHand;

    public Inventory(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getOwner() {
        return uuid;
    }

    public Map<Integer, Item> getCrafting() {
        return crafting;
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

    public Item getHand() {
        return hand;
    }

    public void setHand(Item hand) {
        this.hand = hand;
    }

    public Item getOffHand() {
        return offHand;
    }

    public void setOffHand(Item offHand) {
        this.offHand = offHand;
    }

    @Override
    public String toString() {
        return String.format(
                "Inventory(uuid=%s,crafting=%s,armor=%s,items=%s,hotbar=%s,hand=%s,off-hand=%s)",
                uuid, crafting, armor, items, hotbar, hand, offHand
        );
    }
}
