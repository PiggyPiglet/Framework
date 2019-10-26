package me.piggypiglet.framework.minecraft.player.inventory;

import com.google.inject.Singleton;
import me.piggypiglet.framework.managers.Manager;
import me.piggypiglet.framework.managers.objects.KeyTypeInfo;
import me.piggypiglet.framework.minecraft.player.inventory.objects.Inventory;
import me.piggypiglet.framework.minecraft.player.inventory.objects.Item;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public final class PlayerInventoryManager extends Manager<Inventory> {
    private static final Inventory DEF = new Inventory(UUID.nameUUIDFromBytes("null".getBytes())) {{
        setOffHand(new Item("null", -1, (short) -1, -1,
                new ArrayList<>(), "null", new ArrayList<>(), false,
                new ArrayList<>(), new HashMap<>()));
    }};

    private final Map<UUID, Inventory> inventories = new ConcurrentHashMap<>();

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
