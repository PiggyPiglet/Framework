package me.piggypiglet.framework.sponge.binding.server;

import me.piggypiglet.framework.json.JsonParser;
import me.piggypiglet.framework.minecraft.server.Server;
import me.piggypiglet.framework.utils.FileUtils;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.ban.BanService;
import org.spongepowered.api.world.World;

import java.net.InetSocketAddress;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public final class SpongeServer implements Server {
    private static final org.spongepowered.api.Server HANDLE = Sponge.getServer();
    private static final String VERSION;

    static {
        String version;

        try {
            version = new JsonParser(
                    "{\"data\":" + FileUtils.readEmbedToString("/mcmod.info", Sponge.class) + "}"
            ).getJsonList("data").get(0).getString("version");
        } catch (Exception e) {
            version = "null";
        }

        VERSION = version;
    }

    @Override
    public String getIP() {
        return HANDLE.getBoundAddress().map(InetSocketAddress::getHostName).orElse("0.0.0.0");
    }

    @Override
    public int getPort() {
        return HANDLE.getBoundAddress().map(InetSocketAddress::getPort).orElse(25565);
    }

    @Override
    public Set<String> getIPBans() {
        return Sponge.getServiceManager().provide(BanService.class)
                .map(s -> s.getIpBans().stream().map(i -> i.getAddress().getHostName()).collect(Collectors.toSet()))
                .orElse(new HashSet<>());
    }

    @Override
    public Set<UUID> getBannedPlayers() {
        return Sponge.getServiceManager().provide(BanService.class)
                .map(s -> s.getProfileBans().stream().map(p -> p.getProfile().getUniqueId()).collect(Collectors.toSet()))
                .orElse(new HashSet<>());
    }

    @Override
    public String getVersion() {
        return Sponge.getGame().getPlatform().getMinecraftVersion().getName();
    }

    @Override
    public String getImplementationVersion() {
        return VERSION;
    }

    @Override
    public Set<UUID> getOperators() {
        throw new UnsupportedOperationException("Sponge does not have operators.");
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
        return HANDLE.getOnlinePlayers().stream().map(Player::getUniqueId).collect(Collectors.toSet());
    }

    @Override
    public Set<UUID> getWorlds() {
        return HANDLE.getWorlds().stream().map(World::getUniqueId).collect(Collectors.toSet());
    }

    @Override
    public Object getHandle() {
        return HANDLE;
    }
}
