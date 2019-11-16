package me.piggypiglet.framework.minecraft.player;

import me.piggypiglet.framework.minecraft.player.inventory.objects.Inventory;
import me.piggypiglet.framework.minecraft.world.World;
import me.piggypiglet.framework.minecraft.world.location.Location;
import me.piggypiglet.framework.utils.SearchUtils;

import java.util.UUID;

public interface Player<T> extends SearchUtils.Searchable {
    int getPing();

    String getIp();

    boolean hasPlayedBefore();

    UUID getUuid();

    boolean isOp();

    boolean isWhitelisted();

    long getFirstPlayed();

    long getLastPlayed();

    boolean isFlying();

    float getFlySpeed();

    float getWalkSpeed();

    int getViewDistance();

    int getFoodLevel();

    double getHealth();

    double getHealthScale();

    int getMaximumAir();

    int getRemainingAir();

    float getExperienceSinceLevel();

    int getTotalExperience();

    int getLevel();

    String getLocale();

    long getPlayerTime();

    long getPlayerTimeOffset();

    String getPlayerWeather();

    float getSaturation();

    boolean canPickupItems();

    double getMaxHealth();

    double getLastDamage();

    int getMaximumNoDamageTicks();

    int getNoDamageTicks();

    int getSleepTicks();

    int getTicksLived();

    Inventory getInventory();

    Location getLocation();

    World getWorld();

    T getHandle();

    boolean hasPermission(String permission);
}