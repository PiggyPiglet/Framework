package me.piggypiglet.framework.nukkit.binding.server;

import cn.nukkit.Server;
import cn.nukkit.level.Level;
import me.piggypiglet.framework.minecraft.api.key.data.KeyFactory;
import me.piggypiglet.framework.minecraft.api.key.data.KeyNames;
import me.piggypiglet.framework.minecraft.api.key.framework.KeyImpl;
import me.piggypiglet.framework.minecraft.api.key.framework.keyable.KeyGroup;
import me.piggypiglet.framework.utils.map.Maps;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class NukkitServer extends me.piggypiglet.framework.minecraft.api.server.Server<Server> {
    private final Server handle;

    public NukkitServer(@NotNull final Server handle) {
        super(NukkitServer::new);
        this.handle = handle;
    }

    @Override
    @NotNull
    protected Map<KeyGroup, KeyImpl<?, Server>> provideKeyFunctions() {
        return Maps.of(new HashMap<KeyGroup, KeyImpl<?, Server>>())
                .key(ServerKeys.ADDRESS).value(KeyFactory.ofNullable(Server::getIp, KeyNames.SERVER_ADDRESS))
                // port isn't nullable, but im bad at coding so
                .key(ServerKeys.PORT).value(KeyFactory.ofNullable(Server::getPort, KeyNames.SERVER_PORT))
                .key(ServerKeys.IP_BANS).value(KeyFactory.ofNullable(Server::getIPBans, KeyNames.SERVER_ADDRESS_BANS))
                //.key(ServerKeys.PLAYER_BANS).value(KeyFactory.ofNullable(server -> server.getNameBans().get.stream().map(OfflinePlayer::getUniqueId).collect(Collectors.toSet()), KeyNames.SERVER_BANNED_PLAYERS))
                .key(ServerKeys.VERSION).value(KeyFactory.ofNullable(Server::getVersion, KeyNames.SERVER_VERSION))
                .key(ServerKeys.IMPLEMENTATION_VERSION).value(KeyFactory.ofNullable(Server::getNukkitVersion, KeyNames.SERVER_IMPLEMENTATION_VERSION))
                //.key(ServerKeys.OPERATORS).value(KeyFactory.ofNullable(server -> server.getOps().stream().map(OfflinePlayer::getUniqueId).collect(Collectors.toSet()), KeyNames.SERVER_OPERATORS))
                .key(ServerKeys.MAX_PLAYERS).value(KeyFactory.ofNullable(Server::getMaxPlayers, KeyNames.SERVER_MAX_PLAYERS))
                //.key(ServerKeys.ONLINE_MODE).value(KeyFactory.ofNullable(Server::geton, KeyNames.SERVER_ONLINE_MODE))
                .key(ServerKeys.PLAYERS).value(KeyFactory.ofNullable(server -> server.getOnlinePlayers().keySet(), KeyNames.SERVER_PLAYERS))
                .key(ServerKeys.WORLDS).value(KeyFactory.ofNullable(server -> server.getLevels().values().stream().map(Level::getId).collect(Collectors.toSet()), KeyNames.SERVER_WORLDS))
                .build();
    }

    @NotNull
    @Override
    public Server getHandle() {
        return handle;
    }

    @Override
    public String getName() {
        return handle.getName();
    }
}
