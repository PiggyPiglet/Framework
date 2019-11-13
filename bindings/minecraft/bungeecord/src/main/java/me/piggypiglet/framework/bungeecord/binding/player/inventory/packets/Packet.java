package me.piggypiglet.framework.bungeecord.binding.player.inventory.packets;

import io.netty.buffer.ByteBuf;
import me.piggypiglet.framework.minecraft.versions.ProtocolVersions;
import net.md_5.bungee.protocol.AbstractPacketHandler;
import net.md_5.bungee.protocol.DefinedPacket;
import net.md_5.bungee.protocol.ProtocolConstants;

public abstract class Packet extends DefinedPacket {
    @Override
    public void read(ByteBuf buf, ProtocolConstants.Direction direction, int protocolVersion) {
        read(buf, direction, ProtocolVersions.fromId(protocolVersion));
    }

    @Override
    public void write(ByteBuf buf, ProtocolConstants.Direction direction, int protocolVersion) {
        write(buf, direction, ProtocolVersions.fromId(protocolVersion));
    }

    protected abstract void read(ByteBuf buf, ProtocolConstants.Direction direction, ProtocolVersions version);

    protected abstract void write(ByteBuf buf, ProtocolConstants.Direction direction, ProtocolVersions version);

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
