package me.piggypiglet.framework.bungeecord.binding.player.inventory;

import com.google.inject.Singleton;
import me.piggypiglet.framework.managers.Manager;
import me.piggypiglet.framework.managers.objects.KeyTypeInfo;
import me.piggypiglet.framework.minecraft.player.inventory.objects.Inventory;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Singleton
public final class InventoryManager extends Manager<Inventory> {
    private static final Inventory DEF = new Inventory(null);

    private final Map<UUID, Inventory> inventories = new HashMap<>();

    @Override
    protected KeyTypeInfo configure(KeyTypeInfo.Builder builder) {
        return builder
                .key(UUID.class)
                    .map(inventories, DEF)
                .build();
    }

    @Override
    protected void insert(Inventory item) {
        inventories.put(item.getOwner(), item);
    }

    @Override
    protected void delete(Inventory item) {
        inventories.remove(item.getOwner());
    }

    @Override
    protected Collection<Inventory> retrieveAll() {
        return inventories.values();
    }
}
