package me.piggypiglet.framework.bukkit.binding.server;

import me.piggypiglet.framework.minecraft.server.Server;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class BukkitServer implements Server<org.bukkit.Server> {
    private static final org.bukkit.Server HANDLE = Bukkit.getServer();

    @Override
    public String getIP() {
        return HANDLE.getIp();
    }

    @Override
    public int getPort() {
        return HANDLE.getPort();
    }

    @Override
    public Set<String> getIPBans() {
        return HANDLE.getIPBans();
    }

    @Override
    public Set<UUID> getBannedPlayers() {
        return fromPlayers(HANDLE.getBannedPlayers()).collect(Collectors.toSet());
    }

    @Override
    public String getVersion() {
        return HANDLE.getVersion();
    }

    @Override
    public String getImplementationVersion() {
        return HANDLE.getBukkitVersion();
    }

    @Override
    public Set<UUID> getOperators() {
        return fromPlayers(HANDLE.getOperators()).collect(Collectors.toSet());
    }

    @Override
    public int getMaxPlayers() {
        return HANDLE.getMaxPlayers();
    }

    @Override
    public boolean isOnlineMode() {
        return HANDLE.getOnlineMode();
    }

    @Override
    public Set<UUID> getPlayers() {
        return fromPlayers(HANDLE.getOnlinePlayers()).collect(Collectors.toSet());
    }

    @Override
    public Set<UUID> getWorlds() {
        return HANDLE.getWorlds().stream().map(World::getUID).collect(Collectors.toSet());
    }

    @Override
    public org.bukkit.Server getHandle() {
        return HANDLE;
    }

    private Stream<UUID> fromPlayers(Collection<? extends OfflinePlayer> players) {
        return players.stream().map(OfflinePlayer::getUniqueId);
    }
}
