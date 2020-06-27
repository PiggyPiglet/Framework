package me.piggypiglet.framework.bukkit.binding.inventory.item;

import me.piggypiglet.framework.minecraft.api.inventory.item.framework.Item;
import me.piggypiglet.framework.minecraft.api.inventory.item.framework.MutableItem;
import me.piggypiglet.framework.minecraft.api.inventory.item.material.Material;
import me.piggypiglet.framework.minecraft.api.key.data.KeyFactory;
import me.piggypiglet.framework.minecraft.api.key.data.KeyNames;
import me.piggypiglet.framework.minecraft.api.key.framework.KeyImpl;
import me.piggypiglet.framework.minecraft.api.key.framework.keyable.KeyEnum;
import me.piggypiglet.framework.utils.ReflectionUtils;
import me.piggypiglet.framework.utils.map.Maps;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public final class BukkitItem extends Item<ItemStack> implements MutableItem {
    private static final Map<String, Integer> IDS = new HashMap<>();

    static {
        final String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        final String nms = "net.minecraft.server." + version + ".IRegistry";

        try {
            final Object registryBlocks = ReflectionUtils.getAccessible(Class.forName(nms).getDeclaredField("ITEM")).get(null);
            final Object registryId = ReflectionUtils.getAccessible(registryBlocks.getClass().getSuperclass().getDeclaredField("b"))
                    .get(registryBlocks);
            final Object[] items = (Object[]) ReflectionUtils.getAccessible(registryId.getClass().getDeclaredField("d")).get(registryId);

            for (int i = 0; i < items.length; ++i) {
                final Object item = items[i];

                if (item != null) {
                    IDS.put(item.toString(), i);
                }
            }
        } catch (final Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    private final ItemStack handle;

    public BukkitItem(@NotNull final ItemStack handle) {
        super(BukkitItem::new);
        this.handle = handle;
    }

    @NotNull
    @Override
    protected Map<KeyEnum, KeyImpl<?, ItemStack>> provideKeyFunctions() {
        return Maps.of(new HashMap<KeyEnum, KeyImpl<?, ItemStack>>())
                .key(ItemKeys.MATERIAL).value(KeyFactory.ofNullable(item -> Material.fromName(item.getType().name()), KeyNames.ITEM_MATERIAL))
                .key(ItemKeys.AMOUNT).value(KeyFactory.ofNullable(ItemStack::getAmount, KeyNames.ITEM_AMOUNT))
                .build();
    }

    @NotNull
    @Override
    public ItemStack getHandle() {
        return handle;
    }
}
