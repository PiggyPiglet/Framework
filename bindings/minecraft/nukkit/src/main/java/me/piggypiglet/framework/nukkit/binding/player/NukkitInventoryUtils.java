package me.piggypiglet.framework.nukkit.binding.player;

import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.nbt.tag.Tag;
import me.piggypiglet.framework.minecraft.player.inventory.objects.Item;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.HashSet;

public final class NukkitInventoryUtils {
    static void transferMap(Map<Integer, Item> og, Map<Integer, Item> inven, int initial, int end) {
        int i = -1;
        for (int j = initial; j <= end; ++j) {
            inven.put(++i, og.get(j));
        }
    }

    static Item fromItem(cn.nukkit.item.Item item) {
        return new Item(
                item.getName(), item.count, item.getDamage(), item.getMaxStackSize(),
                Arrays.asList(item.getLore()), item.getCustomName(),
                Arrays.stream(item.getEnchantments()).collect(Collectors.toMap(e -> e.type.name(), Enchantment::getLevel)),
                item.isUnbreakable(),
                item.getNamedTag() == null ? new HashSet<>() : item.getNamedTag().getAllTags().stream().map(Tag::getName).collect(Collectors.toSet())
        );
    }
}
