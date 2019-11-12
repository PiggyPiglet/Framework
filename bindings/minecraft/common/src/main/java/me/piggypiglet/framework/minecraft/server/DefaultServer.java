package me.piggypiglet.framework.minecraft.server;

import me.piggypiglet.framework.utils.annotations.reflection.Disabled;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Disabled
public final class DefaultServer implements Server<DefaultServer> {
    @Override
    public String getIP() {
        return "127.0.0.1";
    }

    @Override
    public int getPort() {
        return -1;
    }

    @Override
    public Set<String> getIPBans() {
        return new HashSet<>();
    }

    @Override
    public Set<UUID> getBannedPlayers() {
        return new HashSet<>();
    }

    @Override
    public String getVersion() {
        return "null";
    }

    @Override
    public String getImplementationVersion() {
        return "null";
    }

    @Override
    public Set<UUID> getOperators() {
        return new HashSet<>();
    }

    @Override
    public int getMaxPlayers() {
        return -1;
    }

    @Override
    public boolean isOnlineMode() {
        return false;
    }

    @Override
    public Set<UUID> getPlayers() {
        return new HashSet<>();
    }

    @Override
    public Set<UUID> getWorlds() {
        return new HashSet<>();
    }

    @Override
    public DefaultServer getHandle() {
        return this;
    }
}
