package me.piggypiglet.framework.nukkit.binding.player;

import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.nbt.tag.Tag;
import me.piggypiglet.framework.minecraft.player.inventory.objects.Item;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public final class NukkitInventoryUtils {
    static void transferMap(Map<Integer, Item> og, Map<Integer, Item> inven, int initial, int end) {
        for (int i = initial; i < end; ++i) {
            inven.put(i, og.get(i));
        }
    }

    static Item fromItem(cn.nukkit.item.Item item) {
        return new Item(
                item.getName(), item.count, item.getDamage(), item.getMaxStackSize(),
                Arrays.asList(item.getLore()), item.getCustomName(),
                Arrays.stream(item.getEnchantments()).collect(Collectors.toMap(e -> e.type.name(), Enchantment::getLevel)),
                item.isUnbreakable(), item.getNamedTag().getAllTags().stream().map(Tag::getName).collect(Collectors.toSet())
        );
    }
}
