/*
 * MIT License
 *
 * Copyright (c) 2019-2020 PiggyPiglet
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

package me.piggypiglet.framework.bukkit.binding.player;

import me.piggypiglet.framework.minecraft.player.inventory.objects.Item;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;
import java.util.stream.Collectors;

final class BukkitPlayerUtils {
    static Map<Integer, Item> fromInventory(Inventory inventory, int initial, int end) {
        final Map<Integer, Item> map = new HashMap<>();

        for (int i = 0; i < end - initial; i++) {
            final ItemStack stack = inventory.getItem(i + initial);

            if (stack != null) {
                map.put(i, fromItemStack(stack));
            }
        }

        return map;
    }

    static Item fromItemStack(ItemStack itemStack) {
        ItemMeta meta = itemStack.getItemMeta();

        int durability = -1;
        List<String> lore = new ArrayList<>();
        String displayName = "null";
        Map<String, Integer> enchants = new HashMap<>();
        boolean unbreakable = false;
        Set<String> itemFlags = new HashSet<>();

        if (meta != null) {
            durability = ((Damageable) meta).getDamage();
            lore = meta.getLore();
            displayName = meta.getDisplayName();
            meta.getEnchants().forEach((k, v) -> enchants.put(k.getKey().getKey(), v));
            unbreakable = meta.isUnbreakable();
            itemFlags = meta.getItemFlags().stream().map(ItemFlag::name).collect(Collectors.toSet());
        }

        return new Item(itemStack.getType().name(), itemStack.getAmount(), durability,
                itemStack.getMaxStackSize(), lore, displayName, enchants, unbreakable, itemFlags
        );
    }
}
