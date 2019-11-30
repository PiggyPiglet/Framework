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

package me.piggypiglet.framework.bungeecord.binding.player.inventory.packets.inventory;

import io.netty.buffer.ByteBuf;
import me.piggypiglet.framework.bungeecord.binding.player.inventory.packets.Packet;
import me.piggypiglet.framework.bungeecord.binding.player.inventory.packets.inventory.item.ItemsUtils;
import me.piggypiglet.framework.minecraft.player.inventory.objects.Item;
import me.piggypiglet.framework.minecraft.versions.ProtocolVersions;
import net.md_5.bungee.protocol.ProtocolConstants;

import java.util.Map;

public final class WindowItems extends Packet {
    private int id = -1;
    private Map<Integer, Item> items = null;

    @Override
    protected void read(ByteBuf buf, ProtocolConstants.Direction direction, ProtocolVersions version) {
        System.out.println("test");

        id = buf.readUnsignedByte();

        if (id == 106) {
            items = ItemsUtils.fromBuf(buf, version);
        }
    }

    @Override
    protected void write(ByteBuf buf, ProtocolConstants.Direction direction, ProtocolVersions version) {
        System.out.println("test2");

        buf.writeByte(id);
        ItemsUtils.toBuf(buf, items, version);
    }

    public int getId() {
        return id;
    }

    public Map<Integer, Item> getItems() {
        return items;
    }
}
