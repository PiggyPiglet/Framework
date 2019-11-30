/*
 * MIT License
 *
 * Copyright (c) 2019 PiggyPiglet
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package me.piggypiglet.framework.bukkit.binding.player;

import me.piggypiglet.framework.minecraft.player.Player;
import me.piggypiglet.framework.minecraft.player.inventory.objects.Inventory;
import me.piggypiglet.framework.minecraft.world.World;
import me.piggypiglet.framework.minecraft.world.location.Location;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import javax.annotation.Nonnull;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Supplier;

import static me.piggypiglet.framework.bukkit.binding.player.BukkitPlayerUtils.fromItemStack;
import static me.piggypiglet.framework.bukkit.binding.player.BukkitPlayerUtils.putFromInventory;

public final class BukkitPlayer implements Player<org.bukkit.entity.Player> {
    private static Method handleMethod;
    private static Field pingField;

    static {
        try {
            final String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];

            handleMethod = Class.forName("org.bukkit.craftbukkit." + version + ".entity.CraftPlayer").getDeclaredMethod("getHandle");
            pingField = Class.forName("net.minecraft.server." + version + ".EntityPlayer").getDeclaredField("ping");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private final org.bukkit.entity.Player player;
    private Object nmsPlayer;

    public BukkitPlayer(@Nonnull org.bukkit.entity.Player player) {
        this.player = player;

        try {
            nmsPlayer = handleMethod.invoke(player);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getPing() {
        try {
            return pingField.getInt(nmsPlayer);
        } catch (Exception e) {
            return -1;
        }
    }

    @Override
    public String getIp() {
        return Objects.requireNonNull(player.getAddress()).getAddress().getHostAddress();
    }

    @Override
    public boolean hasPlayedBefore() {
        return player.hasPlayedBefore();
    }

    @Override
    public UUID getUuid() {
        return player.getUniqueId();
    }

    @Override
    public boolean isOp() {
        return player.isOp();
    }

    @Override
    public boolean isWhitelisted() {
        return player.isWhitelisted();
    }

    @Override
    public long getFirstPlayed() {
        return player.getFirstPlayed();
    }

    @Override
    public long getLastPlayed() {
        return player.getLastPlayed();
    }

    @Override
    public boolean isFlying() {
        return player.isFlying();
    }

    @Override
    public float getFlySpeed() {
        return player.getFlySpeed();
    }

    @Override
    public float getWalkSpeed() {
        return player.getWalkSpeed();
    }

    @Override
    public int getViewDistance() {
        return player.getClientViewDistance();
    }

    @Override
    public int getFoodLevel() {
        return player.getFoodLevel();
    }

    @Override
    public double getHealth() {
        return player.getHealth();
    }

    @Override
    public double getHealthScale() {
        return player.getHealthScale();
    }

    @Override
    public int getMaximumAir() {
        return player.getMaximumAir();
    }

    @Override
    public int getRemainingAir() {
        return player.getRemainingAir();
    }

    @Override
    public float getExperienceSinceLevel() {
        return player.getExp();
    }

    @Override
    public int getTotalExperience() {
        return player.getTotalExperience();
    }

    @Override
    public int getLevel() {
        return player.getLevel();
    }

    @Override
    public String getLocale() {
        return player.getLocale();
    }

    @Override
    public long getPlayerTime() {
        return player.getPlayerTime();
    }

    @Override
    public long getPlayerTimeOffset() {
        return player.getPlayerTimeOffset();
    }

    @Override
    public String getPlayerWeather() {
        return player.getPlayerWeather() == null ? "null" : player.getPlayerWeather().name();
    }

    @Override
    public float getSaturation() {
        return player.getSaturation();
    }

    @Override
    public boolean canPickupItems() {
        return player.getCanPickupItems();
    }

    @Override
    public double getMaxHealth() {
        final AttributeInstance attr = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);

        return attr == null ? -1 : attr.getValue();
    }

    @Override
    public double getLastDamage() {
        return player.getLastDamage();
    }

    @Override
    public int getMaximumNoDamageTicks() {
        return player.getMaximumNoDamageTicks();
    }

    @Override
    public int getNoDamageTicks() {
        return player.getNoDamageTicks();
    }

    @Override
    public int getSleepTicks() {
        return player.getSleepTicks();
    }

    @Override
    public int getTicksLived() {
        return player.getTicksLived();
    }

    @Override
    public Inventory getInventory() {
        final PlayerInventory inventory = player.getInventory();

        return new Inventory(getUuid()) {
            {
                setHand(fromItemStack(inventory.getItemInMainHand()));
                setOffHand(fromItemStack(inventory.getItemInOffHand()));

                new HashMap<Integer, Supplier<ItemStack>>() {{
                    put(0, inventory::getBoots);
                    put(1, inventory::getLeggings);
                    put(2, inventory::getChestplate);
                    put(3, inventory::getHelmet);
                }}.forEach((k, v) -> {
                    final ItemStack stack = v.get();

                    if (stack != null) {
                        getArmor().put(k, fromItemStack(stack));
                    }
                });

                putFromInventory(inventory, getHotbar(), 0, 9);
                putFromInventory(inventory, getItems(), 9, 36);
            }
        };
    }

    @Override
    public Location getLocation() {
        final org.bukkit.Location location = player.getLocation();
        return new Location(getWorld(), location.getBlock().getBiome().name(), location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
    }

    @Override
    public World getWorld() {
        final org.bukkit.World world = player.getLocation().getWorld();

        if (world != null) {
            return new World(world.getUID(), world.getName(), world.getTime());
        }

        return null;
    }

    @Override
    public org.bukkit.entity.Player getHandle() {
        return player;
    }

    @Override
    public boolean hasPermission(String permission) {
        return player.hasPermission(permission);
    }

    @Override
    public String getName() {
        return player.getName();
    }
}