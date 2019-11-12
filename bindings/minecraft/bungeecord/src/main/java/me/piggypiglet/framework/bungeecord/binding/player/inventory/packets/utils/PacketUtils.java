package me.piggypiglet.framework.bungeecord.binding.player.inventory.packets.utils;

import net.md_5.bungee.protocol.DefinedPacket;
import net.md_5.bungee.protocol.Protocol;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public final class PacketUtils {
    private static final PacketRegistrar REGISTER_SERVER_PACKET;
    private static final PacketRegistrar REGISTER_CLIENT_PACKET;
    private static final Method MAP;

    static {
        final Object instance = Enum.valueOf(Protocol.class, "GAME");
        final Class clazz = Protocol.class;
        final List<PacketRegistrar> methods = new ArrayList<>();
        Method mapMethod = null;
        boolean success = false;

        try {
            final Method map = clazz.getDeclaredMethod("map", int.class, int.class);
            map.setAccessible(true);
            mapMethod = map;

            final Class protocolWrapper = map.getReturnType();

            for (String str : new String[]{"TO_SERVER", "TO_CLIENT"}) {
                final Field field = clazz.getDeclaredField(str);
                field.setAccessible(true);

                final Object dataDirection = field.get(instance);
                final Method register = field.get(instance).getClass().getDeclaredMethod("registerPacket", Class.class, Array.newInstance(protocolWrapper, 0).getClass());
                register.setAccessible(true);
                methods.add(new PacketRegistrar(register, dataDirection));
            }

            success = true;
        } catch (Exception ignored) {}

        MAP = mapMethod;

        if (success) {
            REGISTER_SERVER_PACKET = methods.get(1);
            REGISTER_CLIENT_PACKET = methods.get(2);
        } else {
            REGISTER_SERVER_PACKET = null;
            REGISTER_CLIENT_PACKET = null;
        }
    }

    public static boolean register(me.piggypiglet.framework.bungeecord.binding.player.inventory.packets.utils.Protocol protocol,
                                   Class<? extends DefinedPacket> packet, ProtocolMapping... protocolMappings) {
        if (MAP == null) return false;

        final List<Object> actualMaps = new ArrayList<>();

        try {
            for (ProtocolMapping map : protocolMappings) {
                actualMaps.add(MAP.invoke(null, map.getProtocolVersion(), map.getPacketId()));
            }
        } catch (Exception e) {
            return false;
        }

        boolean success;

        try {
            final PacketRegistrar registrar;

            switch (protocol) {
                case TO_SERVER:
                    registrar = REGISTER_SERVER_PACKET;
                    break;

                case TO_CLIENT:
                    registrar = REGISTER_CLIENT_PACKET;
                    break;

                default:
                    return false;
            }

            registrar.getRegistar().invoke(registrar.getInstance(), actualMaps.toArray());

            success = true;
        } catch (Exception e) {
            success = false;
        }

        return success;
    }

    public static ProtocolMapping map(int protocol, int id) {
        return new ProtocolMapping(protocol, id);
    }
}
