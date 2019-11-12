package me.piggypiglet.framework.minecraft.world.location;

import me.piggypiglet.framework.minecraft.world.World;

public class Location {
    private final World world;
    private final String biome;
    private final double x;
    private final double y;
    private final double z;
    private final float yaw;
    private final float pitch;

    public Location(World world, String biome, double x, double y, double z, float yaw, float pitch) {
        this.world = world;
        this.biome = biome;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public World getWorld() {
        return world;
    }

    public String getBiome() {
        return biome;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public float getYaw() {
        return yaw;
    }

    public float getPitch() {
        return pitch;
    }
}
