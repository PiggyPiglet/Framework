package me.piggypiglet.framework.minecraft.world.location;

public final class Location {
    private final String world;
    private final double x;
    private final double y;
    private final double z;

    public Location(String world, double x, double y, double z) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public String getWorld() {
        return world;
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
}
