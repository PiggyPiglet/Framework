package me.piggypiglet.framework.bukkit.binding.server;

import me.piggypiglet.framework.minecraft.api.key.data.KeyFactory;
import me.piggypiglet.framework.minecraft.api.key.data.KeyNames;
import me.piggypiglet.framework.minecraft.api.key.framework.KeyImpl;
import me.piggypiglet.framework.minecraft.api.key.framework.keyable.KeyEnum;
import me.piggypiglet.framework.utils.map.Maps;
import org.bukkit.Server;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class BukkitServer extends me.piggypiglet.framework.minecraft.api.server.Server<Server> {
    private final Server handle;

    public BukkitServer(@NotNull final Server handle) {
        super(BukkitServer::new);
        this.handle = handle;
    }

    @Override
    @NotNull
    protected Map<KeyEnum, KeyImpl<?, Server>> provideKeyFunctions() {
        return Maps.of(new HashMap<KeyEnum, KeyImpl<?, Server>>())
                .key(ServerKeys.ADDRESS).value(KeyFactory.ofNullable(Server::getIp, KeyNames.SERVER_ADDRESS))
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
