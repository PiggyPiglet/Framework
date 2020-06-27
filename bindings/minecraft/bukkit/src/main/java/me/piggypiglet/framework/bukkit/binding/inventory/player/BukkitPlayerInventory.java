package me.piggypiglet.framework.bukkit.binding.inventory.player;

import me.piggypiglet.framework.bukkit.binding.inventory.item.BukkitItem;
import me.piggypiglet.framework.minecraft.api.inventory.framework.MutableInventory;
import me.piggypiglet.framework.minecraft.api.inventory.item.framework.Item;
import me.piggypiglet.framework.minecraft.api.key.framework.KeyImpl;
import me.piggypiglet.framework.minecraft.api.key.framework.keyable.KeyEnum;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

public final class BukkitPlayerInventory
        extends me.piggypiglet.framework.minecraft.api.inventory.framework.player.PlayerInventory<PlayerInventory>
        implements MutableInventory {
    private final PlayerInventory handle;

    public BukkitPlayerInventory(@NotNull final PlayerInventory handle) {
        super(BukkitPlayerInventory::new);
        this.handle = handle;
    }

    @SuppressWarnings("unchecked")
    @NotNull
    @Override
    public Optional<Item<?>>[] getAll() {
        final ItemStack[] contents = handle.getContents();

        return Arrays.stream(contents)
                .map(content -> content == null ? Optional.empty() : Optional.of(new BukkitItem(content)))
                .toArray(Optional[]::new);
    }

    @Override
    protected void handleUpdate(final int slot, final @NotNull Item<?> item) {
     //   handle.setItem(slot, );
    }

    @NotNull
    @Override
    protected Map<KeyEnum, KeyImpl<?, PlayerInventory>> provideKeyFunctions() {
        return null;
    }

    @NotNull
    @Override
    public PlayerInventory getHandle() {
        return handle;
    }

    private static ItemStack from(@NotNull final Item<?> item) {
     //   final Material material = item.get(Keys.ITEM_MATERIAL);
        return null;
    }
}
