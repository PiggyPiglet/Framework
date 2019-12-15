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

package me.piggypiglet.framework.sponge.binding.player;

import me.piggypiglet.framework.minecraft.player.inventory.objects.Item;
import org.spongepowered.api.CatalogType;
import org.spongepowered.api.data.type.HandType;
import org.spongepowered.api.item.enchantment.Enchantment;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.spongepowered.api.data.key.Keys.*;

final class SpongePlayerItemUtils {
    static Map<Integer, Item> fromInventory(Inventory inventory, int end) {
        final Map<Integer, Item> map = new HashMap<>();

        int i = 0;
        for (Inventory item : inventory) {
            if (i == end) {
                return map;
            }

            final Optional<ItemStack> stack = item.peek();

            if (stack.isPresent()) {
                map.put(i, fromItemStack(stack.get()));
            }

            ++i;
        }

        return map;
    }

    static Item hand(org.spongepowered.api.entity.living.player.Player handle, HandType type) {
        return handle.getItemInHand(type).map(SpongePlayerItemUtils::fromItemStack).orElse(null);
    }

    static Item fromItemStack(ItemStack itemStack) {
        return new Item(
                itemStack.getType().getName(),
                itemStack.getQuantity(),
                itemStack.get(ITEM_DURABILITY).orElse(-1),
                itemStack.getMaxStackQuantity(),
                itemStack.get(ITEM_LORE).orElse(new ArrayList<>()).stream()
                        .map(Text::toPlain)
                        .collect(Collectors.toList()),
                itemStack.get(DISPLAY_NAME).map(Text::toPlain).orElse("null"),
                itemStack.get(ITEM_ENCHANTMENTS)
                        .map(l -> l.stream().collect(Collectors.toMap(e -> e.getType().getName(), Enchantment::getLevel)))
                        .orElse(new HashMap<>()),
                itemStack.get(UNBREAKABLE).orElse(false),
                Stream.of(
                        HIDE_ATTRIBUTES, HIDE_CAN_DESTROY, HIDE_CAN_PLACE,
                        HIDE_ENCHANTMENTS, HIDE_MISCELLANEOUS, HIDE_UNBREAKABLE
                ).filter(f -> itemStack.get(f).orElse(false)).map(CatalogType::getName).collect(Collectors.toSet())
        );
    }
}
