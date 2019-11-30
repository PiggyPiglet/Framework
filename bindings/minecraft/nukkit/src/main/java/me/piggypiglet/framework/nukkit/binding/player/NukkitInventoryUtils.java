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

package me.piggypiglet.framework.nukkit.binding.player;

import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.nbt.tag.Tag;
import me.piggypiglet.framework.minecraft.player.inventory.objects.Item;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Collectors;

public final class NukkitInventoryUtils {
    static void transferMap(Map<Integer, Item> og, Map<Integer, Item> inven, int initial, int end) {
        int i = -1;
        for (int j = initial; j <= end; ++j) {
            inven.put(++i, og.get(j));
        }
    }

    static Item fromItem(cn.nukkit.item.Item item) {
        return new Item(
                item.getName(), item.count, item.getDamage(), item.getMaxStackSize(),
                Arrays.asList(item.getLore()), item.getCustomName(),
                Arrays.stream(item.getEnchantments()).collect(Collectors.toMap(e -> e.type.name(), Enchantment::getLevel)),
                item.isUnbreakable(),
                item.getNamedTag() == null ? new HashSet<>() : item.getNamedTag().getAllTags().stream().map(Tag::getName).collect(Collectors.toSet())
        );
    }
}
