package me.piggypiglet.framework.sponge.binding.server;

import me.piggypiglet.framework.minecraft.api.key.data.KeyFactory;
import me.piggypiglet.framework.minecraft.api.key.data.KeyNames;
import me.piggypiglet.framework.minecraft.api.key.framework.KeyImpl;
import me.piggypiglet.framework.minecraft.api.key.framework.keyable.KeyGroup;
import me.piggypiglet.framework.utils.map.Maps;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.Server;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.world.World;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class SpongeServer extends me.piggypiglet.framework.minecraft.api.server.Server<Server> {
    private final Server handle;

    public SpongeServer(@NotNull final Server handle) {
        super(SpongeServer::new);
        this.handle = handle;
    }

    @Override
    @NotNull
    protected Map<KeyGroup, KeyImpl<?, Server>> provideKeyFunctions() {
        return Maps.of(new HashMap<KeyGroup, KeyImpl<?, Server>>())
                .key(ServerKeys.ADDRESS).value(KeyFactory.of(Server::getBoundAddress, KeyNames.SERVER_ADDRESS))
                //.key(ServerKeys.IP_BANS).value(KeyFactory.ofNullable(Sponge.getServiceManager().provide(BanService.class).get().getIpBans().stream().collect(Collectors.toSet()), KeyNames.SERVER_ADDRESS_BANS))
                .key(ServerKeys.MAX_PLAYERS).value(KeyFactory.ofNullable(Server::getMaxPlayers, KeyNames.SERVER_MAX_PLAYERS))
                .key(ServerKeys.ONLINE_MODE).value(KeyFactory.ofNullable(Server::getOnlineMode, KeyNames.SERVER_ONLINE_MODE))
                .key(ServerKeys.PLAYERS).value(KeyFactory.ofNullable(server -> server.getOnlinePlayers().stream().map(Player::getUniqueId).collect(Collectors.toSet()), KeyNames.SERVER_PLAYERS))
                .key(ServerKeys.WORLDS).value(KeyFactory.ofNullable(server -> server.getWorlds().stream().map(World::getUniqueId).collect(Collectors.toSet()), KeyNames.SERVER_WORLDS))
                .build();
    }

    @NotNull
    @Override
    public Server getHandle() {
        return handle;
    }

    // This needs to be fixed
    @Override
    public String getName() {
        return handle.getDefaultWorldName();
    }
}
