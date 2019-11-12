package me.piggypiglet.framework.bungeecord.binding.player.inventory.packets;

import io.netty.buffer.ByteBuf;
import net.md_5.bungee.protocol.AbstractPacketHandler;
import net.md_5.bungee.protocol.DefinedPacket;
import net.md_5.bungee.protocol.ProtocolConstants;

public abstract class Packet extends DefinedPacket {
    @Override
    public abstract void read(ByteBuf buf, ProtocolConstants.Direction direction, int protocolVersion);

    @Override
    public abstract void write(ByteBuf buf, ProtocolConstants.Direction direction, int protocolVersion);

    @Override
    public void handle(AbstractPacketHandler handler) throws Exception {}

    @Override
    public boolean equals(Object obj) {
        return obj.equals(this);
    }

    @Override
    public native int hashCode();

    @Override
    public String toString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode());
    }
}
