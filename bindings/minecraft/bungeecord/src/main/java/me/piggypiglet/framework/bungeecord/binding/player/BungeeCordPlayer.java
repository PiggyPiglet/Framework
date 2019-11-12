package me.piggypiglet.framework.bungeecord.binding.player;

import me.piggypiglet.framework.minecraft.player.Player;
import me.piggypiglet.framework.minecraft.player.inventory.objects.Inventory;
import me.piggypiglet.framework.minecraft.world.World;
import me.piggypiglet.framework.minecraft.world.location.Location;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.UUID;

public final class BungeeCordPlayer implements Player<ProxiedPlayer> {
    private final ProxiedPlayer player;

    public BungeeCordPlayer(ProxiedPlayer player) {
        this.player = player;
    }

    @Override
    public int getPing() {
        return player.getPing();
    }

    @Override
    public String getIp() {
        return player.getAddress().getHostName();
    }

    @Override
    public boolean hasPlayedBefore() {
        throw new UnsupportedOperationException("BungeeCord does not store whether a player has played before.");
    }

    @Override
    public UUID getUuid() {
        return player.getUniqueId();
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
        return 0;
    }

    @Override
    public long getLastPlayed() {
        return 0;
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
        return 0;
    }

    @Override
    public float getWalkSpeed() {
        return 0;
    }

    @Override
    public int getViewDistance() {
        return 0;
    }

    @Override
    public int getFoodLevel() {
        return 0;
    }

    @Override
    public double getHealth() {
        return 0;
    }

    @Override
    public double getHealthScale() {
        return 0;
    }

    @Override
    public int getMaximumAir() {
        return 0;
    }

    @Override
    public int getRemainingAir() {
        return 0;
    }

    @Override
    public float getExp() {
        return 0;
    }

    @Override
    public int getTotalExperience() {
        return 0;
    }

    @Override
    public int getLevel() {
        return 0;
    }

    @Override
    public String getLocale() {
        return null;
    }

    @Override
    public long getPlayerTime() {
        return 0;
    }

    @Override
    public long getPlayerTimeOffset() {
        return 0;
    }

    @Override
    public String getPlayerWeather() {
        return null;
    }

    @Override
    public float getSaturation() {
        return 0;
    }

    @Override
    public boolean canPickupItems() {
        return false;
    }

    @Override
    public double getMaxHealth() {
        return 0;
    }

    @Override
    public double getLastDamage() {
        return 0;
    }

    @Override
    public int getMaximumNoDamageTicks() {
        return 0;
    }

    @Override
    public int getNoDamageTicks() {
        return 0;
    }

    @Override
    public int getSleepTicks() {
        return 0;
    }

    @Override
    public int getTicksLived() {
        return 0;
    }

    @Override
    public Inventory getInventory() {
        return null;
    }

    @Override
    public Location getLocation() {
        return null;
    }

    @Override
    public World getWorld() {
        return null;
    }

    @Override
    public ProxiedPlayer getHandle() {
        return player;
    }

    @Override
    public boolean hasPermission(String permission) {
        return false;
    }

    @Override
    public String getName() {
        return null;
    }
}
