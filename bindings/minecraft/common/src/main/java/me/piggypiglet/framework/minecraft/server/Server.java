package me.piggypiglet.framework.minecraft.server;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface Server {
    String getIP();

    int getPort();

    Set<String> getIPBans();

    Set<UUID> getBannedPlayers();

    String getVersion();

    String getImplementationVersion();

    Set<UUID> getOperators();

    int getMaxPlayers();

    boolean isOnlineMode();

    Set<UUID> getPlayers();

    List<String> getWorlds();
}
