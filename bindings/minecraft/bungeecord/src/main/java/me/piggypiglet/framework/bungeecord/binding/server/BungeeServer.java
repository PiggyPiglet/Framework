package me.piggypiglet.framework.bungeecord.binding.server;

import me.piggypiglet.framework.minecraft.api.key.data.KeyFactory;
import me.piggypiglet.framework.minecraft.api.key.data.KeyNames;
import me.piggypiglet.framework.minecraft.api.key.framework.KeyImpl;
import me.piggypiglet.framework.minecraft.api.key.framework.keyable.KeyEnum;
import me.piggypiglet.framework.minecraft.api.server.Server;
import me.piggypiglet.framework.utils.map.Maps;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public final class BungeeServer extends Server<BungeeServer> {
    private final ProxyServer proxyHandle;
    private final net.md_5.bungee.api.connection.Server serverHandle;

    public BungeeServer(@NotNull final ProxyServer proxyHandle, @NotNull final net.md_5.bungee.api.connection.Server serverHandle) {
        super(BungeeServer::new);
        this.proxyHandle = proxyHandle;
        this.serverHandle = serverHandle;
    }

    protected BungeeServer(@NotNull final BungeeServer server) {
        this(server.proxyHandle, server.serverHandle);
    }

    @NotNull
    @Override
    protected Map<KeyEnum, KeyImpl<?, BungeeServer>> provideKeyFunctions() {
        return Maps.of(new HashMap<KeyEnum, KeyImpl<?, BungeeServer>>())
                .key(ServerKeys.ADDRESS).value(KeyFactory.ofNullable(server -> server.serverHandle.getAddress(), KeyNames.SERVER_ADDRESS))
                .key(ServerKeys.PLAYERS).value(KeyFactory.ofNullable(server -> server.proxyHandle.getPlayers(), KeyNames.SERVER_PLAYERS))
                .build();
    }

    @NotNull
    @Override
    public BungeeServer getHandle() {
        return this;
    }

    @NotNull
    public ProxyServer getProxyHandle() {
        return proxyHandle;
    }

    @NotNull
    public net.md_5.bungee.api.connection.Server getServerHandle() {
        return serverHandle;
    }

    @Override
    public String getName() {
        return null;
    }
}