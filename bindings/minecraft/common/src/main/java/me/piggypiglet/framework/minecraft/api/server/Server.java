package me.piggypiglet.framework.minecraft.api.server;

import me.piggypiglet.framework.minecraft.api.key.data.KeyNames;
import me.piggypiglet.framework.minecraft.api.key.framework.keyable.KeyGroup;
import me.piggypiglet.framework.minecraft.api.key.framework.keyable.Keyable;
import me.piggypiglet.framework.utils.SearchUtils;
import org.jetbrains.annotations.NotNull;

public abstract class Server<H> extends Keyable<H> implements SearchUtils.Searchable {
    public Server() {
        super(ServerKeys.values(), ServerKeys.UNKNOWN);
    }

    protected enum ServerKeys implements KeyGroup {
        ADDRESS(KeyNames.SERVER_ADDRESS),
        PORT(KeyNames.SERVER_PORT),
        IP_BANS(KeyNames.SERVER_ADDRESS_BANS),
        PLAYER_BANS(KeyNames.SERVER_BANNED_PLAYERS),
        VERSION(KeyNames.SERVER_VERSION),
        IMPLEMENTATION_VERSION(KeyNames.SERVER_IMPLEMENTATION_VERSION),
        OPERATORS(KeyNames.SERVER_OPERATORS),
        MAX_PLAYERS(KeyNames.SERVER_MAX_PLAYERS),
        ONLINE_MODE(KeyNames.SERVER_ONLINE_MODE),
        PLAYERS(KeyNames.SERVER_PLAYERS),
        WORLDS(KeyNames.SERVER_WORLDS),
        UNKNOWN(KeyNames.UNKNOWN);

        private final KeyNames parent;

        ServerKeys(@NotNull final KeyNames parent) {
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
