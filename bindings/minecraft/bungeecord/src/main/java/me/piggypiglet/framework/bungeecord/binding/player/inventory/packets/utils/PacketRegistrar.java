package me.piggypiglet.framework.bungeecord.binding.player.inventory.packets.utils;

import java.lang.reflect.Method;

public final class PacketRegistrar {
    private final Method registar;
    private final Object instance;

    public PacketRegistrar(Method registar, Object instance) {
        this.registar = registar;
        this.instance = instance;
    }

    public Method getRegistar() {
        return registar;
    }

    public Object getInstance() {
        return instance;
    }
}
