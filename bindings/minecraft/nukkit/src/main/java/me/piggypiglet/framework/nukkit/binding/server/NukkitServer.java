package me.piggypiglet.framework.nukkit.binding.server;

import cn.nukkit.Player;
import cn.nukkit.level.Level;
import cn.nukkit.plugin.Plugin;
import com.google.inject.Inject;
import me.piggypiglet.framework.guice.objects.MainBinding;
import me.piggypiglet.framework.minecraft.server.Server;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Nukkit doesn't seem to use uuids, so on any method that returns uuids, in any form, it'll just be generated from the name's bytes.
 */
public final class NukkitServer implements Server<cn.nukkit.Server> {
    private final cn.nukkit.Server handle;

    @Inject
    public NukkitServer(MainBinding main) {
        handle = ((Plugin) main.getInstance()).getServer();
    }

    @Override
    public String getIP() {
        return handle.getIp();
    }

    @Override
    public int getPort() {
        return handle.getPort();
    }

    @Override
    public Set<String> getIPBans() {
        return handle.getIPBans().getEntires().keySet();
    }

    @Override
    public Set<UUID> getBannedPlayers() {
        return generateUuids(handle.getNameBans().getEntires().keySet());
    }

    @Override
    public String getVersion() {
        return handle.getVersion();
    }

    @Override
    public String getImplementationVersion() {
        return handle.getApiVersion();
    }

    @Override
    public Set<UUID> getOperators() {
        return generateUuids(handle.getOps().getAll().keySet());
    }

    @Override
    public int getMaxPlayers() {
        return handle.getMaxPlayers();
    }

    @Override
    public boolean isOnlineMode() {
        return true;
    }

    @Override
    public Set<UUID> getPlayers() {
        return generateUuids(handle.getOnlinePlayers().values().stream().map(Player::getName).collect(Collectors.toSet()));
    }

    @Override
    public Set<UUID> getWorlds() {
        return generateUuids(handle.getLevels().values().stream().map(Level::getName).collect(Collectors.toSet()));
    }

    @Override
    public cn.nukkit.Server getHandle() {
        return handle;
    }

    private Set<UUID> generateUuids(Set<String> names) {
        return names.stream().map(String::getBytes).map(UUID::nameUUIDFromBytes).collect(Collectors.toSet());
    }
}
