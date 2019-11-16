package me.piggypiglet.framework.bungeecord.registerables;

import me.piggypiglet.framework.bungeecord.binding.player.inventory.packets.inventory.WindowItems;
import me.piggypiglet.framework.bungeecord.binding.player.inventory.packets.utils.PacketUtils;
import me.piggypiglet.framework.bungeecord.binding.player.inventory.packets.utils.Protocol;
import me.piggypiglet.framework.minecraft.versions.ProtocolVersions;
import me.piggypiglet.framework.registerables.StartupRegisterable;

public final class PacketRegisterable extends StartupRegisterable {
    @Override
    protected void execute() {
        PacketUtils.register(Protocol.TO_CLIENT, WindowItems.class, PacketUtils.maps(14, ProtocolVersions.values()));
    }
}
