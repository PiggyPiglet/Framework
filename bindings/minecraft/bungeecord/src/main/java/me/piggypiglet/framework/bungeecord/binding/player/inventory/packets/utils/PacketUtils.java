package me.piggypiglet.framework.bungeecord.binding.player.inventory.packets.utils;

import com.google.common.base.Preconditions;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.TObjectIntMap;
import me.piggypiglet.framework.minecraft.versions.ProtocolVersions;
import net.md_5.bungee.protocol.DefinedPacket;
import net.md_5.bungee.protocol.ProtocolConstants;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;

import static me.piggypiglet.framework.utils.ReflectionUtils.getAccessible;

@SuppressWarnings("unchecked")
public final class PacketUtils {
    private static final Class PROTOCOL = net.md_5.bungee.protocol.Protocol.class;
    private static final Object GAME = net.md_5.bungee.protocol.Protocol.GAME;
    private static final Field PROTOCOLS;
    private static final Method MAP_GET;
    private static final Method MAP_PUT;
    private static final Object TO_SERVER;
    private static final Object TO_CLIENT;
    private static final Field PACKET_MAP;
    private static final Field CONSTRUCTORS;

    static {
        Field protocols = null;
        Method mapGet = null;
        Method mapPut = null;
        Object toServer = null;
        Object toClient = null;
        Field packetMap = null;
        Field constructors = null;

        try {
            protocols = getAccessible(Class.forName("net.md_5.bungee.protocol.Protocol$DirectionData").getDeclaredField("protocols"));
            mapGet = getAccessible(TIntObjectMap.class.getMethod("get", int.class));
            mapPut = getAccessible(TObjectIntMap.class.getMethod("put", Object.class, int.class));
        } catch (Exception ignored) {}

        PROTOCOLS = protocols;
        MAP_GET = mapGet;
        MAP_PUT = mapPut;

        try {
            toServer = directionData("TO_SERVER");
            toClient = directionData("TO_CLIENT");

            final Class protocolData = Class.forName("net.md_5.bungee.protocol.Protocol$ProtocolData");
            packetMap = getAccessible(protocolData.getDeclaredField("packetMap"));
            constructors = getAccessible(protocolData.getDeclaredField("packetConstructors"));
        } catch (Exception ignored) {}

        TO_SERVER = toServer;
        TO_CLIENT = toClient;
        PACKET_MAP = packetMap;
        CONSTRUCTORS = constructors;
    }

    public static boolean register(Protocol direction, Class<? extends DefinedPacket> packet, ProtocolMapping... mappings) {
        final Object instance;

        switch (direction) {
            case TO_SERVER:
                instance = TO_SERVER;
                break;

            case TO_CLIENT:
                instance = TO_CLIENT;
                break;

            default:
                return false;
        }

        boolean success;

        try {
            final Constructor<? extends DefinedPacket> constructor = packet.getDeclaredConstructor();

            int mappingIndex = 0;
            ProtocolMapping mapping = mappings[mappingIndex];
            for (int protocol : ProtocolConstants.SUPPORTED_VERSION_IDS) {
                final Set<Integer> ids = mapping.getVersion().getIds();
                final int packetId = mapping.getPacketId();

                if (ids.stream().allMatch(i -> i > protocol)) {
                    continue;
                }

                if (ids.stream().allMatch(i -> i < protocol) && mappingIndex + 1 < mappings.length) {
                    final ProtocolMapping nextMapping = mappings[mappingIndex + 1];
                    final Set<Integer> nextIds = nextMapping.getVersion().getIds();

                    if (nextIds.stream().anyMatch(i -> i == protocol)) {
                        Preconditions.checkState(nextMapping.getPacketId() != packetId, "Duplicate packet mapping (%s, %s)", ids, nextIds);

                        mapping = nextMapping;
                        mappingIndex++;
                    }
                }

                Object protocolData = MAP_GET.invoke(PROTOCOLS.get(instance), protocol);
                MAP_PUT.invoke(PACKET_MAP.get(protocolData), packet, packetId);
                ((Constructor<? extends DefinedPacket>[]) CONSTRUCTORS.get(protocolData))[packetId] = constructor;
            }

            success = true;
        } catch (Exception e) {
            e.printStackTrace();
            success = false;
        }

        return success;
    }

    public static ProtocolMapping[] maps(int id, ProtocolVersions... versions) {
        return Arrays.stream(versions).map(v -> new ProtocolMapping(v, id)).toArray(ProtocolMapping[]::new);
    }

    private static Object directionData(String field) throws Exception {
        return getAccessible(PROTOCOL.getDeclaredField(field)).get(GAME);
    }
}
