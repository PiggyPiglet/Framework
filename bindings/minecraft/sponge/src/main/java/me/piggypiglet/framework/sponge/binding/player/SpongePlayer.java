package me.piggypiglet.framework.sponge.binding.player;

import me.piggypiglet.framework.minecraft.player.Player;
import me.piggypiglet.framework.minecraft.player.inventory.objects.Inventory;
import me.piggypiglet.framework.minecraft.player.inventory.objects.Item;
import me.piggypiglet.framework.minecraft.world.World;
import me.piggypiglet.framework.minecraft.world.location.Location;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.type.HandType;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.Transform;
import org.spongepowered.api.item.inventory.entity.PlayerInventory;
import org.spongepowered.api.service.whitelist.WhitelistService;

import java.util.UUID;
import java.util.function.Consumer;

import static me.piggypiglet.framework.sponge.binding.player.SpongePlayerItemUtils.putFromInventory;

public final class SpongePlayer implements Player<org.spongepowered.api.entity.living.player.Player> {
    private final org.spongepowered.api.entity.living.player.Player handle;

    public SpongePlayer(org.spongepowered.api.entity.living.player.Player handle) {
        this.handle = handle;
    }

    @Override
    public int getPing() {
        return handle.getConnection().getLatency();
    }

    @Override
    public String getIp() {
        return handle.getConnection().getAddress().getHostName();
    }

    @Override
    public boolean hasPlayedBefore() {
        return handle.hasPlayedBefore();
    }

    @Override
    public UUID getUuid() {
        return handle.getUniqueId();
    }

    @Override
    public boolean isOp() {
        throw new UnsupportedOperationException("Sponge does not have operators.");
    }

    @Override
    public boolean isWhitelisted() {
        return Sponge.getServiceManager().provide(WhitelistService.class)
                .map(w -> w.isWhitelisted(handle.getProfile()))
                .orElseThrow(() -> new UnsupportedOperationException("Whitelist service isn't present."));
    }

    @Override
    public long getFirstPlayed() {
        return handle.firstPlayed().get().toEpochMilli();
    }

    @Override
    public long getLastPlayed() {
        return handle.lastPlayed().get().toEpochMilli();
    }

    @Override
    public boolean isFlying() {
        return handle.get(Keys.IS_FLYING).orElse(false);
    }

    @Override
    public float getFlySpeed() {
        return (float) (double) handle.get(Keys.FLYING_SPEED).orElse(1D);
    }

    @Override
    public float getWalkSpeed() {
        return (float) (double) handle.get(Keys.WALKING_SPEED).orElse(1D);
    }

    @Override
    public int getViewDistance() {
        return handle.getViewDistance();
    }

    @Override
    public int getFoodLevel() {
        return handle.get(Keys.FOOD_LEVEL).orElse(0);
    }

    @Override
    public double getHealth() {
        return handle.get(Keys.HEALTH).orElse(0D);
    }

    @Override
    public double getHealthScale() {
        return handle.get(Keys.HEALTH_SCALE).orElse(0D);
    }

    @Override
    public int getMaximumAir() {
        return handle.get(Keys.MAX_AIR).orElse(0);
    }

    @Override
    public int getRemainingAir() {
        return handle.get(Keys.REMAINING_AIR).orElse(0);
    }

    @Override
    public float getExperienceSinceLevel() {
        return handle.get(Keys.EXPERIENCE_SINCE_LEVEL).orElse(0);
    }

    @Override
    public int getTotalExperience() {
        return handle.get(Keys.TOTAL_EXPERIENCE).orElse(0);
    }

    @Override
    public int getLevel() {
        return handle.get(Keys.EXPERIENCE_LEVEL).orElse(0);
    }

    @Override
    public String getLocale() {
        return handle.getLocale().getLanguage();
    }

    @Override
    public long getPlayerTime() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public long getPlayerTimeOffset() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public String getPlayerWeather() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public float getSaturation() {
        return (float) (double) handle.get(Keys.SATURATION).orElse(0D);
    }

    @Override
    public boolean canPickupItems() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public double getMaxHealth() {
        return handle.get(Keys.MAX_HEALTH).orElse(20D);
    }

    @Override
    public double getLastDamage() {
        return handle.get(Keys.LAST_DAMAGE).map(o -> o.orElse(0D)).get();
    }

    @Override
    public int getMaximumNoDamageTicks() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public int getNoDamageTicks() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public int getSleepTicks() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public int getTicksLived() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public Inventory getInventory() {
        final PlayerInventory inventory = (PlayerInventory) handle.getInventory();

        return new Inventory(getUuid()) {
            {
                hand(HandTypes.MAIN_HAND, this::setHand);
                hand(HandTypes.OFF_HAND, this::setOffHand);

                putFromInventory(inventory.getEquipment(), getArmor(), 0, 4);
                putFromInventory(inventory.getHotbar(), getHotbar(), 0, 9);
                putFromInventory(inventory.getMain(), getItems(), 0, 27);
            }

            private void hand(HandType type, Consumer<Item> set) {
                handle.getItemInHand(type).map(SpongePlayerItemUtils::fromItemStack).ifPresent(set);
            }
        };
    }

    @Override
    public Location getLocation() {
        final org.spongepowered.api.world.Location<org.spongepowered.api.world.World> location = handle.getLocation();
        final Transform<org.spongepowered.api.world.World> transform = handle.getTransform();

        return new Location(
                getWorld(),
                location.getBiome().getName(),
                location.getX(),
                location.getY(),
                location.getZ(),
                (float) transform.getYaw(),
                (float) transform.getPitch()
        );
    }

    @Override
    public World getWorld() {
        final org.spongepowered.api.world.World world = handle.getWorld();

        return new World(
                world.getUniqueId(),
                world.getName(),
                world.getProperties().getWorldTime()
        );
    }

    @Override
    public org.spongepowered.api.entity.living.player.Player getHandle() {
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
