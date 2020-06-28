package me.piggypiglet.framework.bukkit.binding.server;

import me.piggypiglet.framework.minecraft.api.key.data.KeyFactory;
import me.piggypiglet.framework.minecraft.api.key.data.KeyNames;
import me.piggypiglet.framework.minecraft.api.key.framework.KeyImpl;
import me.piggypiglet.framework.minecraft.api.key.framework.keyable.KeyGroup;
import me.piggypiglet.framework.minecraft.api.versions.Versions;
import me.piggypiglet.framework.utils.map.Maps;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class BukkitServer extends me.piggypiglet.framework.minecraft.api.server.Server<Server> {
    private static final Server HANDLE = Bukkit.getServer();

    @Override
    @NotNull
    protected Map<KeyGroup, KeyImpl<?, Server>> provideKeyFunctions() {
        return Maps.of(new HashMap<KeyGroup, KeyImpl<?, Server>>())
                .key(ServerKeys.ADDRESS).value(KeyFactory.ofNullable(Server::getIp, KeyNames.SERVER_ADDRESS))
                // port isn't nullable, but im bad at coding so
                .key(ServerKeys.PORT).value(KeyFactory.ofNullable(Server::getPort, KeyNames.SERVER_PORT))
                .key(ServerKeys.IP_BANS).value(KeyFactory.ofNullable(Server::getIPBans, KeyNames.SERVER_ADDRESS_BANS))
                .key(ServerKeys.PLAYER_BANS).value(KeyFactory.ofNullable(server -> server.getBannedPlayers().stream().map(OfflinePlayer::getUniqueId).collect(Collectors.toSet()), KeyNames.SERVER_BANNED_PLAYERS))
                .key(ServerKeys.VERSION).value(KeyFactory.ofNullable(
                        server -> {
                            final String[] parts = server.getBukkitVersion().split("\\.", 3);
                            return Versions.fromName(String.join("_", parts[0], parts[1]));
                        },
                        KeyNames.SERVER_VERSION
                ))
                .key(ServerKeys.IMPLEMENTATION_VERSION).value(KeyFactory.ofNullable(Server::getBukkitVersion, KeyNames.SERVER_IMPLEMENTATION_VERSION))
                .key(ServerKeys.OPERATORS).value(KeyFactory.ofNullable(server -> server.getOperators().stream().map(OfflinePlayer::getUniqueId).collect(Collectors.toSet()), KeyNames.SERVER_OPERATORS))
                .key(ServerKeys.MAX_PLAYERS).value(KeyFactory.ofNullable(Server::getMaxPlayers, KeyNames.SERVER_MAX_PLAYERS))
                .key(ServerKeys.ONLINE_MODE).value(KeyFactory.ofNullable(Server::getOnlineMode, KeyNames.SERVER_ONLINE_MODE))
                .key(ServerKeys.PLAYERS).value(KeyFactory.ofNullable(server -> server.getOnlinePlayers().stream().map(Player::getUniqueId).collect(Collectors.toSet()), KeyNames.SERVER_PLAYERS))
                .key(ServerKeys.WORLDS).value(KeyFactory.ofNullable(server -> server.getWorlds().stream().map(World::getUID).collect(Collectors.toSet()), KeyNames.SERVER_WORLDS))
                .build();
    }

    @NotNull
    @Override
    public Server getHandle() {
        return HANDLE;
    }

    @Override
    public String getName() {
        return HANDLE.getName();
    }
}
