package me.piggypiglet.framework.minecraft.player;

import me.piggypiglet.framework.utils.SearchUtils;

import java.util.UUID;

public abstract class Player<T> implements SearchUtils.Searchable {
    public abstract int getPing();

    public abstract String getIp();

    public abstract boolean isPlayedBefore();

    public abstract UUID getUuid();

    public abstract boolean isOp();

    public abstract boolean isWhitelisted();

    public abstract long getFirstPlayed();

    public abstract long getLastPlayed();

    public abstract boolean isFly();

    public abstract boolean isFlying();

    public abstract float getFlySpeed();

    public abstract float getWalkSpeed();

    public abstract int getViewDistance();

    public abstract int getFoodLevel();

    public abstract double getHealth();

    public abstract double getHealthScale();

    public abstract int getMaximumAir();

    public abstract int getRemainingAir();

    public abstract float getExp();

    public abstract int getTotalExperience();

    public abstract int getLevel();

    public abstract String getLocale();

    public abstract long getPlayerTime();

    public abstract long getPlayerTimeOffset();

    public abstract String getPlayerWeather();

    public abstract float getSaturation();

    public abstract boolean isPickupItems();

    public abstract int getMaxHealth();

    public abstract double getLastDamage();

    public abstract int getMaximumNoDamageTicks();

    public abstract int getNoDamageTicks();

    public abstract int getSleepTicks();

    public abstract int getTicksLived();

    public abstract T getHandle();

    public abstract boolean hasPermission(String permission);
}