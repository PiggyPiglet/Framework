package me.piggypiglet.framework.bungeecord.binding.player.inventory.packets.inventory;

import io.netty.buffer.ByteBuf;
import me.piggypiglet.framework.bungeecord.binding.player.inventory.packets.Packet;
import me.piggypiglet.framework.minecraft.player.inventory.objects.Item;
import net.md_5.bungee.protocol.ProtocolConstants;

public final class WindowItems extends Packet {
    private int id;
    private Item[] items;

    @Override
    public void read(ByteBuf buf, ProtocolConstants.Direction direction, int protocolVersion) {
        id = buf.readUnsignedByte();

        int i = buf.readShort();
        items = new Item[i];

        for (int j = 0; j < i; j++) {
            items[j] = ItemUtils.fromBuf(buf, protocolVersion);
        }
    }

    @Override
    public void write(ByteBuf buf, ProtocolConstants.Direction direction, int protocolVersion) {
        buf.writeByte(id);
        buf.writeShort(items.length);

        for (Item item : items) {
            ItemUtils.toBuf(item, protocolVersion);
        }
    }

    public int getId() {
        return id;
    }

    public Item[] getItems() {
        return items;
    }
}
