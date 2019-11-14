package me.piggypiglet.framework.bungeecord.binding.player.inventory.packets.inventory.item.construction.versions;

import io.netty.buffer.ByteBuf;
import me.piggypiglet.framework.bungeecord.binding.player.inventory.packets.inventory.item.construction.ItemsConstructor;
import me.piggypiglet.framework.minecraft.player.inventory.objects.Item;

import java.util.HashMap;
import java.util.Map;

public final class ItemsConstructor1710 implements ItemsConstructor {
    @Override
    public Map<Integer, Item> from(ByteBuf buf) {
        final Map<Integer, Item> map = new HashMap<>(46);
        short count = buf.readShort();

        System.out.println(count);

        return null;
    }

    @Override
    public void to(ByteBuf buf, Map<Integer, Item> items) {

    }
}
