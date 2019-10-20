package me.piggypiglet.framework.minecraft.world.location;

import me.piggypiglet.framework.minecraft.world.World;

public final class Location {
    private World world;
    private String biome;
    private double x;
    private double y;
    private double z;

    public Location(World world, String biome, double x, double y, double z) {
        this.world = world;
        this.biome = biome;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public String getBiome() {
        return biome;
    }

    public void setBiome(String biome) {
        this.biome = biome;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }
}
