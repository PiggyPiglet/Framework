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
        id = buf.readUnsignedByte();

        if (id == 0) {
            items = ItemsUtils.fromBuf(buf, version);
        }
    }

    @Override
    protected void write(ByteBuf buf, ProtocolConstants.Direction direction, ProtocolVersions version) {
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
