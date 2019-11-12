package me.piggypiglet.framework.bungeecord.binding.player.inventory.packets.utils;

public final class ProtocolMapping {
    private final int protocolVersion;
    private final int packetId;

    public ProtocolMapping(int protocolVersion, int packetId) {
        this.protocolVersion = protocolVersion;
        this.packetId = packetId;
    }

    public int getProtocolVersion() {
        return protocolVersion;
    }

    public int getPacketId() {
        return packetId;
    }
}
