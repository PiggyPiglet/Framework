package me.piggypiglet.framework.bukkit.binding.player;

import me.piggypiglet.framework.minecraft.api.key.data.KeyFactory;
import me.piggypiglet.framework.minecraft.api.key.data.KeyNames;
import me.piggypiglet.framework.minecraft.api.key.framework.KeyImpl;
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
                .key(PlayerKeys.NAME).value(FACTORY.ofNullable(Player::getName, KeyNames.NAME))
                //.key(PlayerKeys.INVENTORY).value(FACTORY.ofNullable(Player::getName, KeyNames.INVENTORY))
                .key(PlayerKeys.HEALTH).value(FACTORY.ofNullable(Player::getHealth, KeyNames.HEALTH))
                .key(PlayerKeys.HEALTH_SCALE).value(FACTORY.ofNullable(Player::getHealthScale, KeyNames.HEALTH_SCALE))
                //.key(PlayerKeys.CHAT_VISIBILITY).value(FACTORY.ofNullable(Player::getName, KeyNames.PLAYER_CHAT_VISIBILITY))
                .key(PlayerKeys.ADDRESS).value(FACTORY.ofNullable(Player::getAddress, KeyNames.PLAYER_ADDRESS))
                //.key(PlayerKeys.ENDER_CHEST_INVENTORY).value(FACTORY.ofNullable(Player::getName, KeyNames.PLAYER_ENDER_CHEST_INVENTORY))
                .build();
    }

    @NotNull
    @Override
    public String getName() {
        return handle.getName();
    }
}
