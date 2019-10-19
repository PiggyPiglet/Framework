package me.piggypiglet.framework.minecraft.player;

import me.piggypiglet.framework.minecraft.player.inventory.Inventory;
import me.piggypiglet.framework.minecraft.world.location.Location;

import java.util.UUID;

public interface Player {
    int getPing();

    String getIp();

    boolean hasPlayedBefore();

    String getName();

    UUID getUuid();

    boolean isOp();

    boolean isWhitelisted();

    long getFirstPlayed();

    long getLastPlayed();

    Location getBedSpawnLocation();

    boolean hasPermission(String permission);

    boolean canFly();

    boolean isFlying();

    float getFlySpeed();

    float getWalkSpeed();

    int getViewDistance();

    float getExp();

    int getTotalExperience();

    int getFoodLevel();

    double getHealth();

    double getHealthScale();

    int getLevel();

    String getLocale();

    long getPlayerTime();

    long getPlayerTimeOffset();

    String getPlayerWeather();

    float getSaturation();

    //todo: stats

    Inventory getInventory();
}
