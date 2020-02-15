/*
 * MIT License
 *
 * Copyright (c) 2019-2020 PiggyPiglet
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

import me.piggypiglet.framework.logging.Logger;
import me.piggypiglet.framework.logging.LoggerFactory;
import me.piggypiglet.framework.minecraft.player.Player;
import me.piggypiglet.framework.minecraft.player.inventory.objects.Inventory;
import me.piggypiglet.framework.minecraft.player.inventory.objects.Item;
import me.piggypiglet.framework.minecraft.world.World;
import me.piggypiglet.framework.minecraft.world.location.Location;
import me.piggypiglet.framework.utils.ReflectionUtils;
import me.piggypiglet.framework.utils.builder.GenericBuilder;
import me.piggypiglet.framework.utils.map.Maps;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.inventory.PlayerInventory;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

import static me.piggypiglet.framework.bukkit.binding.player.BukkitPlayerUtils.fromInventory;
import static me.piggypiglet.framework.bukkit.binding.player.BukkitPlayerUtils.fromItemStack;

public final class BukkitPlayer implements Player<org.bukkit.entity.Player> {
    private static final Logger<?> LOGGER = LoggerFactory.getLogger("");

    private static Method handleMethod;
    private static Field pingField;
    private static Field connection;
    private static Method sendPacket;
    private static Constructor<?> packetPlayOutChat;
    private static Method fromJson;

    static {
        try {
            final String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
            final String cb = "org.bukkit.craftbukkit." + version + ".";
            final String nms = "net.minecraft.server." + version + ".";

            handleMethod = Class.forName(cb + "entity.CraftPlayer").getDeclaredMethod("getHandle");

            final Class<?> entityPlayer = handleMethod.getReturnType();

            pingField = ReflectionUtils.getAccessible(entityPlayer.getDeclaredField("ping"));
            connection = ReflectionUtils.getAccessible(entityPlayer.getDeclaredField("playerConnection"));
            sendPacket = ReflectionUtils.getAccessible(connection.getType().getDeclaredMethod("sendPacket", Class.forName(nms + "Packet")));

            final Class<?> packet = Class.forName(nms + "PacketPlayOutChat");
            final Class<?> component = Class.forName(nms + "IChatBaseComponent");

            packetPlayOutChat = packet.getConstructor(component);
            fromJson = ReflectionUtils.getAccessible(component.getDeclaredClasses()[0].getDeclaredMethod("a", String.class));
        } catch (Exception e) {
            LOGGER.error(e);
        }
    }

    private final org.bukkit.entity.Player player;
    private Object nmsPlayer;

    public BukkitPlayer(@NotNull org.bukkit.entity.Player player) {
        this.player = player;

        try {
            nmsPlayer = handleMethod.invoke(player);
        } catch (Exception e) {
            LOGGER.error(e);
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

        return GenericBuilder.of(() -> new Inventory(getUuid()))
                .with(Inventory::setHand, fromItemStack(inventory.getItemInMainHand()))
                .with(Inventory::setOffHand, fromItemStack(inventory.getItemInOffHand()))
                .with(Inventory::setArmor, Maps.of(new HashMap<Integer, Item>(), BukkitPlayerUtils::fromItemStack)
                        .key(0).value(inventory.getBoots())
                        .key(1).value(inventory.getLeggings())
                        .key(2).value(inventory.getChestplate())
                        .key(3).value(inventory.getHelmet())
                        .build())
                .with(Inventory::setHotbar, fromInventory(inventory, 0, 9))
                .with(Inventory::setItems, fromInventory(inventory, 9, 36))
                .build();
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

    public void sendRawMessage(String json) {
        try {
            sendPacket.invoke(
                    connection.get(handleMethod.invoke(getHandle())),
                    packetPlayOutChat.newInstance(fromJson.invoke(null, json))
            );
        } catch (Exception e) {
            LOGGER.error(e);
        }
    }
}