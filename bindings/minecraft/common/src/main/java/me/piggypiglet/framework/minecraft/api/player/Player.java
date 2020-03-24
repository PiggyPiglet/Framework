package me.piggypiglet.framework.minecraft.api.player;

import me.piggypiglet.framework.logging.LoggerFactory;
import me.piggypiglet.framework.logging.framework.Logger;
import me.piggypiglet.framework.minecraft.api.key.data.KeyFactory;
import me.piggypiglet.framework.minecraft.api.key.data.KeyNames;
import me.piggypiglet.framework.minecraft.api.key.framework.Key;
import me.piggypiglet.framework.minecraft.api.key.framework.KeyImpl;
import me.piggypiglet.framework.minecraft.api.key.framework.Keyable;
import me.piggypiglet.framework.utils.SearchUtils;
import me.piggypiglet.framework.utils.builder.GenericBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

public abstract class Player<H> implements SearchUtils.Searchable, Keyable {
    private static final Logger<?> LOGGER = LoggerFactory.getLogger("Player");
    private static final KeyImpl<?, ?> DEFAULT_KEY =
            KeyFactory.initFactoryFromHandle().ofNullable(redundant -> null, KeyNames.UNKNOWN);

    private Map<PlayerKeys, KeyImpl<?, H>> keyFunctions = new HashMap<>();

    public void setup() {
        final Map<PlayerKeys, KeyImpl<?, H>> keyFunctions = provideKeyFunctions();
        final List<PlayerKeys> difference = GenericBuilder.of(() -> new ArrayList<>(Arrays.asList(PlayerKeys.values())))
                .with(list -> list.remove(PlayerKeys.UNKNOWN))
                .with(List::removeAll, keyFunctions.keySet())
                .build();

        final String str = difference.stream().map(Enum::toString).collect(Collectors.joining(", "));

        if (!difference.isEmpty()) {
            LOGGER.warning("The following keys haven't been implemented for the %s player implementation: %s",
                    getHandle().getClass(), str);
        }

        this.keyFunctions = keyFunctions;
    }

    public abstract void sendRawMessage(@NotNull final String json);

    @NotNull
    public abstract H getHandle();

    @NotNull
    protected abstract Map<PlayerKeys, KeyImpl<?, H>> provideKeyFunctions();

    @SuppressWarnings("unchecked")
    @Override
    @NotNull
    public <V> Optional<V> get(@NotNull final Key<V, ?> key) {
        return (Optional<V>) keyFunctions.getOrDefault(PlayerKeys.fromKey(key), (KeyImpl<?, H>) DEFAULT_KEY).get(getHandle());
    }

    protected enum PlayerKeys {
        UUID(KeyNames.UUID),
        NAME(KeyNames.NAME),
        INVENTORY(KeyNames.INVENTORY),
        HEALTH(KeyNames.HEALTH),
        HEALTH_SCALE(KeyNames.HEALTH_SCALE),
        CHAT_VISIBILITY(KeyNames.PLAYER_CHAT_VISIBILITY),
        ADDRESS(KeyNames.PLAYER_ADDRESS),
        ENDER_CHEST_INVENTORY(KeyNames.PLAYER_ENDER_CHEST_INVENTORY),
        UNKNOWN(KeyNames.UNKNOWN);

        private final KeyNames name;

        PlayerKeys(@NotNull final KeyNames name) {
            this.name = name;
        }

        private static PlayerKeys fromKey(@NotNull final Key<?, ?> key) {
            for (PlayerKeys name : values()) {
                if (key.getName() == name.name) {
                    return name;
                }
            }

            return UNKNOWN;
        }
    }
}