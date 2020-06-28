package me.piggypiglet.framework.minecraft.api.player;

import me.piggypiglet.framework.minecraft.api.key.data.KeyNames;
import me.piggypiglet.framework.minecraft.api.key.framework.keyable.KeyGroup;
import me.piggypiglet.framework.minecraft.api.key.framework.keyable.Keyable;
import me.piggypiglet.framework.utils.SearchUtils;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public abstract class Player<H> extends Keyable<H> implements SearchUtils.Searchable {
    @SuppressWarnings({"unchecked", "rawtypes"})
    public Player(@NotNull final Function<H, Player<H>> initializer) {
        super(PlayerKeys.values(), PlayerKeys.UNKNOWN, (Function) initializer);
    }

    public abstract void sendRawMessage(@NotNull final String json);

    protected enum PlayerKeys implements KeyGroup {
        UUID(KeyNames.UUID),
        NAME(KeyNames.NAME),
        INVENTORY(KeyNames.INVENTORY),
        HEALTH(KeyNames.ENTITY_HEALTH),
        HEALTH_SCALE(KeyNames.ENTITY_HEALTH_SCALE),
        CHAT_VISIBILITY(KeyNames.PLAYER_CHAT_VISIBILITY),
        ADDRESS(KeyNames.PLAYER_ADDRESS),
        ENDER_CHEST_INVENTORY(KeyNames.PLAYER_ENDER_CHEST_INVENTORY),
        UNKNOWN(KeyNames.UNKNOWN);

        private final KeyNames parent;

        PlayerKeys(@NotNull final KeyNames parent) {
            this.parent = parent;
        }

        @NotNull
        public KeyNames getParent() {
            return parent;
        }

        @NotNull
        @Override
        public String getName() {
            return name();
        }
    }
}