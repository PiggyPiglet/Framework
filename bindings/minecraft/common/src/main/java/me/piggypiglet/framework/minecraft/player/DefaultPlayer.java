package me.piggypiglet.framework.minecraft.player;

import me.piggypiglet.framework.minecraft.player.inventory.objects.Inventory;

import java.util.UUID;

public final class DefaultPlayer implements Player<Player> {
    @Override
    public int getPing() {
        return -1;
    }

    @Override
    public String getIp() {
        return "null";
    }

    @Override
    public boolean hasPlayedBefore() {
        return false;
    }

    @Override
    public UUID getUuid() {
        return null;
    }

    @Override
    public boolean isOp() {
        return false;
    }

    @Override
    public boolean isWhitelisted() {
        return false;
    }

    @Override
    public long getFirstPlayed() {
        return -1;
    }

    @Override
    public long getLastPlayed() {
        return -1;
    }

    @Override
    public boolean canFly() {
        return false;
    }

    @Override
    public boolean isFlying() {
        return false;
    }

    @Override
    public float getFlySpeed() {
        return -1;
    }

    @Override
    public float getWalkSpeed() {
        return -1;
    }

    @Override
    public int getViewDistance() {
        return -1;
    }

    @Override
    public int getFoodLevel() {
        return -1;
    }

    @Override
    public double getHealth() {
        return -1;
    }

    @Override
    public double getHealthScale() {
        return -1;
    }

    @Override
    public int getMaximumAir() {
        return -1;
    }

    @Override
    public int getRemainingAir() {
        return -1;
    }

    @Override
    public float getExp() {
        return -1;
    }

    @Override
    public int getTotalExperience() {
        return -1;
    }

    @Override
    public int getLevel() {
        return -1;
    }

    @Override
    public String getLocale() {
        return "null";
    }

    @Override
    public long getPlayerTime() {
        return -1;
    }

    @Override
    public long getPlayerTimeOffset() {
        return -1;
    }

    @Override
    public String getPlayerWeather() {
        return "null";
    }

    @Override
    public float getSaturation() {
        return -1;
    }

    @Override
    public boolean canPickupItems() {
        return false;
    }

    @Override
    public double getMaxHealth() {
        return -1;
    }

    @Override
    public double getLastDamage() {
        return -1;
    }

    @Override
    public int getMaximumNoDamageTicks() {
        return -1;
    }

    @Override
    public int getNoDamageTicks() {
        return -1;
    }

    @Override
    public int getSleepTicks() {
        return -1;
    }

    @Override
    public int getTicksLived() {
        return -1;
    }

    @Override
    public Inventory getInventory() {
        return null;
    }

    @Override
    public Player getHandle() {
        return this;
    }

    @Override
    public boolean hasPermission(String permission) {
        return false;
    }

    @Override
    public String getName() {
        return "null";
    }
}
