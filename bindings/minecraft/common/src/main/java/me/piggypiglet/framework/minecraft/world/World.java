package me.piggypiglet.framework.minecraft.world;

import java.util.UUID;

public class World {
    private final UUID uuid;
    private final String name;
    private final long time;

    public World(UUID uuid, String name, long time) {
        this.uuid = uuid;
        this.name = name;
        this.time = time;
    }

    public UUID getUUID() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public long getTime() {
        return time;
    }
}
