package me.piggypiglet.framework.bukkit.binding.player;

import me.piggypiglet.framework.minecraft.player.inventory.objects.Item;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;
import java.util.stream.Collectors;

final class BukkitPlayerUtils {
    static void putFromInventory(Inventory inventory, Map<Integer, Item> map, int initial, int size) {
        for (int i = initial; i < size; i++) {
            final ItemStack stack = inventory.getItem(i);

            if (stack != null) {
                map.put(i, fromItemStack(stack));
            }
        }
    }

    static Item fromItemStack(ItemStack itemStack) {
        ItemMeta meta = itemStack.getItemMeta();

        int durability = -1;
        List<String> lore = new ArrayList<>();
        String displayName = "null";
        Map<String, Integer> enchants = new HashMap<>();
        boolean unbreakable = false;
        Set<String> itemFlags = new HashSet<>();

        if (meta != null) {
            durability = ((Damageable) meta).getDamage();
            lore = meta.getLore();
            displayName = meta.getDisplayName();
            meta.getEnchants().forEach((k, v) -> enchants.put(k.getKey().getKey(), v));
            unbreakable = meta.isUnbreakable();
            itemFlags = meta.getItemFlags().stream().map(ItemFlag::name).collect(Collectors.toSet());
        }

        return new Item(itemStack.getType().name(), itemStack.getAmount(), durability,
                itemStack.getMaxStackSize(), lore, displayName, enchants, unbreakable, itemFlags
        );
    }
}
