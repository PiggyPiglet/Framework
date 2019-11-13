package me.piggypiglet.framework.bungeecord.binding.player.inventory.packets.inventory;

import io.netty.buffer.ByteBuf;
import me.piggypiglet.framework.bungeecord.binding.player.inventory.packets.Packet;
import me.piggypiglet.framework.bungeecord.binding.player.inventory.packets.inventory.item.ItemUtils;
import me.piggypiglet.framework.minecraft.player.inventory.objects.Item;
import me.piggypiglet.framework.minecraft.versions.ProtocolVersions;
import net.md_5.bungee.protocol.ProtocolConstants;

public final class WindowItems extends Packet {
    private int id;
    private Item[] items;

    @Override
    protected void read(ByteBuf buf, ProtocolConstants.Direction direction, ProtocolVersions version) {
        id = buf.readUnsignedByte();

        int i = buf.readShort();
        items = new Item[i];

        for (int j = 0; j < i; j++) {
            items[j] = ItemUtils.fromBuf(buf, version);
        }
    }

    @Override
    protected void write(ByteBuf buf, ProtocolConstants.Direction direction, ProtocolVersions version) {
        buf.writeByte(id);
        buf.writeShort(items.length);

        for (Item item : items) {
            ItemUtils.toBuf(buf, item, version);
        }
    }

    public int getId() {
        return id;
    }

    public Item[] getItems() {
        return items;
    }
}
