package me.piggypiglet.framework.bungeecord.binding.player.inventory.packets.inventory.item;

import io.netty.buffer.ByteBuf;
import me.piggypiglet.framework.bungeecord.binding.player.inventory.packets.inventory.item.construction.ItemConstructor;
import me.piggypiglet.framework.bungeecord.binding.player.inventory.packets.inventory.item.construction.versions.ItemConstructor1132;
import me.piggypiglet.framework.bungeecord.binding.player.inventory.packets.inventory.item.construction.versions.ItemConstructor1710;
import me.piggypiglet.framework.minecraft.player.inventory.objects.Item;
import me.piggypiglet.framework.minecraft.versions.ProtocolVersions;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static me.piggypiglet.framework.minecraft.versions.ProtocolVersions.*;

public final class ItemUtils {
    private static final Map<ProtocolVersions, ItemConstructor> CONSTRUCTORS = new HashMap<>();

    static {
        final ItemConstructor item1132 = new ItemConstructor1132();
        final ItemConstructor item1710 = new ItemConstructor1710();

        put(item1132, V1_14, V1_13);
        put(item1710, V1_12, V1_11, V1_10, V1_9, V1_8, V1_7);
    }

    public static Item fromBuf(ByteBuf buf, ProtocolVersions version) {
        return CONSTRUCTORS.get(version).from(buf);
    }

    public static void toBuf(ByteBuf buf, Item item, ProtocolVersions version) {
        CONSTRUCTORS.get(version).to(buf, item);
    }

    private static void put(ItemConstructor constructor, ProtocolVersions... versions) {
        Arrays.stream(versions).forEach(m -> CONSTRUCTORS.put(m, constructor));
    }
}
