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

package me.piggypiglet.framework.bungeecord.binding.player.inventory.packets.inventory.item;

import io.netty.buffer.ByteBuf;
import me.piggypiglet.framework.bungeecord.binding.player.inventory.packets.inventory.item.construction.ItemsConstructor;
import me.piggypiglet.framework.bungeecord.binding.player.inventory.packets.inventory.item.construction.versions.ItemsConstructor1132;
import me.piggypiglet.framework.bungeecord.binding.player.inventory.packets.inventory.item.construction.versions.ItemsConstructor1710;
import me.piggypiglet.framework.minecraft.player.inventory.objects.Item;
import me.piggypiglet.framework.minecraft.versions.ProtocolVersions;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static me.piggypiglet.framework.minecraft.versions.ProtocolVersions.*;

public final class ItemsUtils {
    private static final Map<ProtocolVersions, ItemsConstructor> CONSTRUCTORS = new HashMap<>();

    static {
        final ItemsConstructor items1132 = new ItemsConstructor1132();
        final ItemsConstructor items1710 = new ItemsConstructor1710();

        put(items1132, V1_14, V1_13);
        put(items1710, V1_12, V1_11, V1_10, V1_9, V1_8, V1_7);
    }

    public static Map<Integer, Item> fromBuf(ByteBuf buf, ProtocolVersions version) {
        return CONSTRUCTORS.get(version).from(buf);
    }

    public static void toBuf(ByteBuf buf, Map<Integer, Item> items, ProtocolVersions version) {
        CONSTRUCTORS.get(version).to(buf, items);
    }

    private static void put(ItemsConstructor constructor, ProtocolVersions... versions) {
        Arrays.stream(versions).forEach(m -> CONSTRUCTORS.put(m, constructor));
    }
}
