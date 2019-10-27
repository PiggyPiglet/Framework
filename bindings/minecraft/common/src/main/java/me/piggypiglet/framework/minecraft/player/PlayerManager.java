package me.piggypiglet.framework.minecraft.player;

import com.google.inject.Singleton;
import me.piggypiglet.framework.managers.implementations.SearchableManager;
import me.piggypiglet.framework.managers.objects.KeyTypeInfo;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public final class PlayerManager extends SearchableManager<Player> {
    private static final Player DEF = new DefaultPlayer();

    private final Map<UUID, Player> players = new ConcurrentHashMap<>();

    @Override
    protected KeyTypeInfo configure(KeyTypeInfo.Builder builder) {
        return builder
                .key(UUID.class)
                    .map(players, DEF)
                .key(String.class)
                    .list(items, (s, p) -> p.getName().equalsIgnoreCase(s), DEF)
                .build();
    }

    @Override
    protected void insert(Player item) {
        players.put(item.getUuid(), item);
    }

    @Override
    protected void delete(Player item) {
        players.remove(item.getUuid());
    }
}
