package me.piggypiglet.framework.nukkit.binding.player;

import cn.nukkit.inventory.PlayerInventory;
import cn.nukkit.level.Level;
import cn.nukkit.level.biome.Biome;
import me.piggypiglet.framework.minecraft.player.Player;
import me.piggypiglet.framework.minecraft.player.inventory.objects.Inventory;
import me.piggypiglet.framework.minecraft.player.inventory.objects.Item;
import me.piggypiglet.framework.minecraft.world.World;
import me.piggypiglet.framework.minecraft.world.location.Location;

import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static me.piggypiglet.framework.nukkit.binding.player.NukkitInventoryUtils.fromItem;

public final class NukkitPlayer implements Player<cn.nukkit.Player> {
    private final cn.nukkit.Player handle;

    public NukkitPlayer(cn.nukkit.Player player) {
        handle = player;
    }

    @Override
    public int getPing() {
        return handle.getPing();
    }

    @Override
    public String getIp() {
        return handle.getAddress();
    }

    @Override
    public boolean hasPlayedBefore() {
        return handle.hasPlayedBefore();
    }

    @Override
    public UUID getUuid() {
        return UUID.nameUUIDFromBytes(handle.getName().getBytes());
    }

    @Override
    public boolean isOp() {
        return handle.isOp();
    }

    @Override
    public boolean isWhitelisted() {
        return handle.isWhitelisted();
    }

    @Override
    public long getFirstPlayed() {
        return handle.getFirstPlayed();
    }

    @Override
    public long getLastPlayed() {
        return handle.getLastPlayed();
    }

    @Override
    public boolean isFlying() {
        throw new UnsupportedOperationException("Nukkit does not store this value.");
    }

    @Override
    public float getFlySpeed() {
        return handle.getMovementSpeed();
    }

    @Override
    public float getWalkSpeed() {
        return handle.getMovementSpeed();
    }

    @Override
    public int getViewDistance() {
        return handle.getViewDistance();
    }

    @Override
    public int getFoodLevel() {
        return handle.getFoodData().getLevel();
    }

    @Override
    public double getHealth() {
        return handle.getHealth();
    }

    @Override
    public double getHealthScale() {
        return 20;
    }

    @Override
    public int getMaximumAir() {
        throw new UnsupportedOperationException("Nukkit does not store this value.");
    }

    @Override
    public int getRemainingAir() {
        throw new UnsupportedOperationException("Nukkit does not store this value.");
    }

    @Override
    public float getExperienceSinceLevel() {
        throw new UnsupportedOperationException("Nukkit does not store this value.");
    }

    @Override
    public int getTotalExperience() {
        return handle.getExperience();
    }

    @Override
    public int getLevel() {
        return handle.getExperienceLevel();
    }

    @Override
    public String getLocale() {
        return handle.getLocale().getLanguage();
    }

    @Override
    public long getPlayerTime() {
        return handle.getLevel().getTime();
    }

    @Override
    public long getPlayerTimeOffset() {
        throw new UnsupportedOperationException("Nukkit does not store this value.");
    }

    @Override
    public String getPlayerWeather() {
        final Level level = handle.getLevel();

        if (level.isRaining()) {
            return "downfall";
        } else if (level.isThundering()) {
            return "thunder";
        } else {
            return "clear";
        }
    }

    @Override
    public float getSaturation() {
        return handle.getFoodData().getFoodSaturationLevel();
    }

    @Override
    public boolean canPickupItems() {
        throw new UnsupportedOperationException("Nukkit does not store this value.");
    }

    @Override
    public double getMaxHealth() {
        return handle.getMaxHealth();
    }

    @Override
    public double getLastDamage() {
        return handle.getLastDamageCause().getDamage();
    }

    @Override
    public int getMaximumNoDamageTicks() {
        throw new UnsupportedOperationException("Nukkit does not store this value.");
    }

    @Override
    public int getNoDamageTicks() {
        return handle.noDamageTicks;
    }

    @Override
    public int getSleepTicks() {
        throw new UnsupportedOperationException("Nukkit does not store this value.");
    }

    @Override
    public int getTicksLived() {
        return handle.ticksLived;
    }

    @Override
    public Inventory getInventory() {
        final PlayerInventory inventory = handle.getInventory();
        final Map<Integer, Item> slots = handle.getInventory().slots.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> fromItem(e.getValue())));

        System.out.println(slots);

        return new Inventory(getUuid()) {
            {
                setHand(fromItem(inventory.getItemInHand()));

//                transferMap(slots, getArmor(), 0, 4);
//                transferMap(slots, getHotbar(), 4, 13);
            }
        };
    }

    @Override
    public Location getLocation() {
        final cn.nukkit.level.Location location = handle.getLocation();
        final double x = location.getX();
        final double z = location.getZ();

        return new Location(
                getWorld(), Biome.getBiome(location.getLevel().getBiomeId((int) x, (int) z)).getName(), x, location.getY(), z,
                (float) location.getYaw(), (float) location.getPitch()
        );
    }

    @Override
    public World getWorld() {
        final Level level = handle.getLevel();

        return new World(UUID.nameUUIDFromBytes(String.valueOf(level.getId()).getBytes()), level.getName(), level.getTime());
    }

    @Override
    public cn.nukkit.Player getHandle() {
        return handle;
    }

    @Override
    public boolean hasPermission(String permission) {
        return handle.hasPermission(permission);
    }

    @Override
    public String getName() {
        return handle.getName();
    }
}
