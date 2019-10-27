package me.piggypiglet.framework.bukkit.binding;

import me.piggypiglet.framework.minecraft.player.Player;
import me.piggypiglet.framework.minecraft.player.inventory.objects.Inventory;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.inventory.PlayerInventory;

import javax.annotation.Nonnull;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.UUID;

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
    public boolean canFly() {
        return player.getAllowFlight();
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
    public float getExp() {
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

//        return new Inventory(getUuid()) {{
//            setOffHand(inventory.getItemInOffHand());
//        }};
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

//    private Item fromItemStack(ItemStack itemStack) {
//        ItemMeta meta = itemStack.getItemMeta();
//
//        return new Item(itemStack.getType().name(), itemStack.getAmount(), (short) ((Damageable) itemStack).getDamage(),
//                itemStack.getMaxStackSize(), meta.getLore(), meta.getDisplayName(),
//                meta.getEnchants().entrySet().stream().collect(Collectors.toMap(e -> ((Map.Entry<Enchantment, Integer>) e).getKey().get, 0)),
//                meta.isUnbreakable(), meta.getItemFlags());
//    }
}