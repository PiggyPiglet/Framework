package me.piggypiglet.framework.bukkit.binding.player;

import me.piggypiglet.framework.minecraft.api.meta.data.KeyFactory;
import me.piggypiglet.framework.minecraft.api.meta.data.KeyNames;
import me.piggypiglet.framework.minecraft.api.meta.framework.KeyImpl;
import me.piggypiglet.framework.utils.map.Maps;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.EnumMap;
import java.util.Map;

public final class BukkitPlayer extends me.piggypiglet.framework.minecraft.api.player.Player<Player> {
    private static final KeyFactory<Player> FACTORY = KeyFactory.initFactoryFromHandle();

    private final Player handle;

    public BukkitPlayer(@NotNull final Player handle) {
        this.handle = handle;
    }

    @Override
    public void sendRawMessage(@NotNull final String json) {
        handle.sendRawMessage(json);
    }

    @NotNull
    @Override
    public Player getHandle() {
        return handle;
    }

    @NotNull
    @Override
    protected Map<PlayerKeys, KeyImpl<?, Player>> provideKeyFunctions() {
        return Maps.of(new EnumMap<PlayerKeys, KeyImpl<?, Player>>(PlayerKeys.class))
                .key(PlayerKeys.UUID).value(FACTORY.ofNullable(Player::getUniqueId, KeyNames.UUID))
                .build();
    }

    @Override
    protected int provideGamemode() {
        switch (handle.getGameMode()) {
            case SURVIVAL:
                return 0;

            case CREATIVE:
                return 1;

            case ADVENTURE:
                return 2;

            case SPECTATOR:
                return 3;

            default:
                return -1;
        }
    }

    @NotNull
    @Override
    public String getName() {
        return handle.getName();
    }
}
