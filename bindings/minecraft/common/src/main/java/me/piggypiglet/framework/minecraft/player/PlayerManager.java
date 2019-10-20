package me.piggypiglet.framework.minecraft.player;

import com.google.inject.Singleton;
import me.piggypiglet.framework.managers.implementations.SearchableManager;
import me.piggypiglet.framework.managers.objects.KeyTypeInfo;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public final class PlayerManager extends SearchableManager<Player> {
    private static final Player DEF = new Player<Player>(-1, "127.0.0.1", false, "null",
            null, false, false, 0,
            0, null, false, false,
            -1f, -1f, -1, -1,
            -1d, -1d, -1, -1,
            -1f, -1, -1, "null",
            -1L, -1L, "null", -1f,
            null, false, null, -1,
            -1d, -1, -1, -1,
            -1) {
        @Override
        public Player getHandle() {
            return this;
        }

        @Override
        public boolean hasPermission(String permission) {
            return false;
        }
    };

    private final Map<UUID, Player> players = new ConcurrentHashMap<>();

    @Override
    protected KeyTypeInfo configure(KeyTypeInfo.Builder builder) {
        return builder
                .key(UUID.class)
                    .map(players, DEF)
                .build();
    }

    @Override
    protected void insert(Player player) {
        players.put(player.getUuid(), player);
    }

    @Override
    protected void delete(Player player) {
        players.remove(player.getUuid());
    }

    @Override
    protected Collection<Player> retrieveAll() {
        return items;
    }
}
