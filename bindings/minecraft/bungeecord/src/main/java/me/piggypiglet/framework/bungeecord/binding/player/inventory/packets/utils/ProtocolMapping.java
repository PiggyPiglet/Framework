package me.piggypiglet.framework.bungeecord.binding.player.inventory.packets.utils;

import me.piggypiglet.framework.minecraft.versions.ProtocolVersions;

public final class ProtocolMapping {
    private final ProtocolVersions version;
    private final int packetId;

    public ProtocolMapping(ProtocolVersions version, int packetId) {
        this.version = version;
        this.packetId = packetId;
    }

    public ProtocolVersions getVersion() {
        return version;
    }

    public int getPacketId() {
        return packetId;
    }
}
