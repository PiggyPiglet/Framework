package me.piggypiglet.framework.bungeecord.binding.player.inventory.packets.inventory.item.construction;

import io.netty.buffer.ByteBuf;
import me.piggypiglet.framework.minecraft.player.inventory.objects.Item;

import java.util.Map;

public interface ItemsConstructor {
    Map<Integer, Item> from(ByteBuf buf);

    void to(ByteBuf buf, Map<Integer, Item> items);
}
