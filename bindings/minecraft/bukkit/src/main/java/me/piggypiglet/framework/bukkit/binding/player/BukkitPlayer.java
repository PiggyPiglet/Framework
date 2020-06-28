package me.piggypiglet.framework.bukkit.binding.player;

import me.piggypiglet.framework.bukkit.binding.inventory.player.BukkitPlayerInventory;
import me.piggypiglet.framework.minecraft.api.key.data.KeyNames;
import me.piggypiglet.framework.minecraft.api.key.framework.KeyImpl;
import me.piggypiglet.framework.minecraft.api.key.framework.keyable.KeyGroup;
import me.piggypiglet.framework.utils.map.Maps;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import static me.piggypiglet.framework.minecraft.api.key.data.KeyFactory.ofNullable;

public final class BukkitPlayer extends me.piggypiglet.framework.minecraft.api.player.Player<Player> {
    private final Player handle;

    public BukkitPlayer(@NotNull final Player handle) {
        super(BukkitPlayer::new);
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

    @Override
    @NotNull
    protected Map<KeyGroup, KeyImpl<?, Player>> provideKeyFunctions() {
        return Maps.of(new HashMap<KeyGroup, KeyImpl<?, Player>>())
                .key(PlayerKeys.UUID).value(ofNullable(Player::getUniqueId, KeyNames.UUID))
                .key(PlayerKeys.NAME).value(ofNullable(Player::getName, KeyNames.NAME))
                .key(PlayerKeys.INVENTORY).value(ofNullable(player -> new BukkitPlayerInventory(player.getInventory()), KeyNames.INVENTORY))
                .key(PlayerKeys.HEALTH).value(ofNullable(Player::getHealth, KeyNames.ENTITY_HEALTH))
                .key(PlayerKeys.HEALTH_SCALE).value(ofNullable(Player::getHealthScale, KeyNames.ENTITY_HEALTH_SCALE))
                //.key(PlayerKeys.CHAT_VISIBILITY).value(FACTORY.ofNullable(Player::getName, KeyNames.PLAYER_CHAT_VISIBILITY))
                .key(PlayerKeys.ADDRESS).value(ofNullable(Player::getAddress, KeyNames.PLAYER_ADDRESS))
                //.key(PlayerKeys.ENDER_CHEST_INVENTORY).value(FACTORY.ofNullable(Player::getName, KeyNames.PLAYER_ENDER_CHEST_INVENTORY))
                .build();
    }

    @NotNull
    @Override
    public String getName() {
        return handle.getName();
    }
}
