package me.piggypiglet.framework.sponge.binding.player;

import me.piggypiglet.framework.minecraft.player.inventory.objects.Item;
import org.spongepowered.api.CatalogType;
import org.spongepowered.api.item.enchantment.Enchantment;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.spongepowered.api.data.key.Keys.*;

final class SpongePlayerItemUtils {
    static void putFromInventory(Inventory inventory, Map<Integer, Item> map, int initial, int end) {
        int i = initial;
        for (Inventory item : inventory) {
            if (i == end) {
                return;
            }

            final Optional<ItemStack> stack = item.peek();

            if (stack.isPresent()) {
                map.put(i, fromItemStack(stack.get()));
            }

            ++i;
        }
    }

    static Item fromItemStack(ItemStack itemStack) {
        return new Item(
                itemStack.getType().getName(),
                itemStack.getQuantity(),
                itemStack.get(ITEM_DURABILITY).orElse(-1),
                itemStack.getMaxStackQuantity(),
                itemStack.get(ITEM_LORE).orElse(new ArrayList<>()).stream()
                        .map(Text::toPlain)
                        .collect(Collectors.toList()),
                itemStack.get(DISPLAY_NAME).map(Text::toPlain).orElse("null"),
                itemStack.get(ITEM_ENCHANTMENTS)
                        .map(l -> l.stream().collect(Collectors.toMap(e -> e.getType().getName(), Enchantment::getLevel)))
                        .orElse(new HashMap<>()),
                itemStack.get(UNBREAKABLE).orElse(false),
                Stream.of(
                        HIDE_ATTRIBUTES, HIDE_CAN_DESTROY, HIDE_CAN_PLACE,
                        HIDE_ENCHANTMENTS, HIDE_MISCELLANEOUS, HIDE_UNBREAKABLE
                ).filter(f -> itemStack.get(f).orElse(false)).map(CatalogType::getName).collect(Collectors.toSet())
        );
    }
}
