package me.piggypiglet.framework.minecraft.player;

import me.piggypiglet.framework.minecraft.player.inventory.Inventory;
import me.piggypiglet.framework.minecraft.world.location.Location;
import me.piggypiglet.framework.utils.SearchUtils;

import java.util.UUID;

public abstract class Player<T> implements SearchUtils.Searchable {
    private int ping;
    private String ip;
    private boolean playedBefore;
    private String name;
    private UUID uuid;
    private boolean op;
    private boolean whitelisted;
    private long firstPlayed;
    private long lastPlayed;
    private Location bedSpawnLocation;
    private boolean fly;
    private boolean flying;
    private float flySpeed;
    private float walkSpeed;
    private int viewDistance;
    private int foodLevel;
    private double health;
    private double healthScale;
    private int maximumAir;
    private int remainingAir;
    private float exp;
    private int totalExperience;
    private int level;
    private String locale;
    private long playerTime;
    private long playerTimeOffset;
    private String playerWeather;
    private float saturation;
    //todo: stats
    private Inventory inventory;
    private boolean pickupItems;
    private Location compassTarget;
    private int maxHealth;
    private double lastDamage;
    private int maximumNoDamageTicks;
    private int noDamageTicks;
    private int sleepTicks;
    private int ticksLived;

    public Player(int ping, String ip, boolean playedBefore,
                  String name, UUID uuid, boolean op,
                  boolean whitelisted, long firstPlayed, long lastPlayed,
                  Location bedSpawnLocation, boolean fly, boolean flying,
                  float flySpeed, float walkSpeed, int viewDistance,
                  int foodLevel, double health, double healthScale,
                  int maximumAir, int remainingAir, float exp,
                  int totalExperience, int level, String locale,
                  long playerTime, long playerTimeOffset, String playerWeather,
                  float saturation, Inventory inventory, boolean pickupItems,
                  Location compassTarget, int maxHealth, double lastDamage,
                  int maximumNoDamageTicks, int noDamageTicks, int sleepTicks,
                  int ticksLived) {
        this.ping = ping;
        this.ip = ip;
        this.playedBefore = playedBefore;
        this.name = name;
        this.uuid = uuid;
        this.op = op;
        this.whitelisted = whitelisted;
        this.firstPlayed = firstPlayed;
        this.lastPlayed = lastPlayed;
        this.bedSpawnLocation = bedSpawnLocation;
        this.fly = fly;
        this.flying = flying;
        this.flySpeed = flySpeed;
        this.walkSpeed = walkSpeed;
        this.viewDistance = viewDistance;
        this.foodLevel = foodLevel;
        this.health = health;
        this.healthScale = healthScale;
        this.maximumAir = maximumAir;
        this.remainingAir = remainingAir;
        this.exp = exp;
        this.totalExperience = totalExperience;
        this.level = level;
        this.locale = locale;
        this.playerTime = playerTime;
        this.playerTimeOffset = playerTimeOffset;
        this.playerWeather = playerWeather;
        this.saturation = saturation;
        this.inventory = inventory;
        this.pickupItems = pickupItems;
        this.compassTarget = compassTarget;
        this.maxHealth = maxHealth;
        this.lastDamage = lastDamage;
        this.maximumNoDamageTicks = maximumNoDamageTicks;
        this.noDamageTicks = noDamageTicks;
        this.sleepTicks = sleepTicks;
        this.ticksLived = ticksLived;
    }

    public int getPing() {
        return ping;
    }

    public void setPing(int ping) {
        this.ping = ping;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public boolean isPlayedBefore() {
        return playedBefore;
    }

    public void setPlayedBefore(boolean playedBefore) {
        this.playedBefore = playedBefore;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public boolean isOp() {
        return op;
    }

    public void setOp(boolean op) {
        this.op = op;
    }

    public boolean isWhitelisted() {
        return whitelisted;
    }

    public void setWhitelisted(boolean whitelisted) {
        this.whitelisted = whitelisted;
    }

    public long getFirstPlayed() {
        return firstPlayed;
    }

    public void setFirstPlayed(long firstPlayed) {
        this.firstPlayed = firstPlayed;
    }

    public long getLastPlayed() {
        return lastPlayed;
    }

    public void setLastPlayed(long lastPlayed) {
        this.lastPlayed = lastPlayed;
    }

    public Location getBedSpawnLocation() {
        return bedSpawnLocation;
    }

    public void setBedSpawnLocation(Location bedSpawnLocation) {
        this.bedSpawnLocation = bedSpawnLocation;
    }

    public boolean isFly() {
        return fly;
    }

    public void setFly(boolean fly) {
        this.fly = fly;
    }

    public boolean isFlying() {
        return flying;
    }

    public void setFlying(boolean flying) {
        this.flying = flying;
    }

    public float getFlySpeed() {
        return flySpeed;
    }

    public void setFlySpeed(float flySpeed) {
        this.flySpeed = flySpeed;
    }

    public float getWalkSpeed() {
        return walkSpeed;
    }

    public void setWalkSpeed(float walkSpeed) {
        this.walkSpeed = walkSpeed;
    }

    public int getViewDistance() {
        return viewDistance;
    }

    public void setViewDistance(int viewDistance) {
        this.viewDistance = viewDistance;
    }

    public int getFoodLevel() {
        return foodLevel;
    }

    public void setFoodLevel(int foodLevel) {
        this.foodLevel = foodLevel;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public double getHealthScale() {
        return healthScale;
    }

    public void setHealthScale(double healthScale) {
        this.healthScale = healthScale;
    }

    public int getMaximumAir() {
        return maximumAir;
    }

    public void setMaximumAir(int maximumAir) {
        this.maximumAir = maximumAir;
    }

    public int getRemainingAir() {
        return remainingAir;
    }

    public void setRemainingAir(int remainingAir) {
        this.remainingAir = remainingAir;
    }

    public float getExp() {
        return exp;
    }

    public void setExp(float exp) {
        this.exp = exp;
    }

    public int getTotalExperience() {
        return totalExperience;
    }

    public void setTotalExperience(int totalExperience) {
        this.totalExperience = totalExperience;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public long getPlayerTime() {
        return playerTime;
    }

    public void setPlayerTime(long playerTime) {
        this.playerTime = playerTime;
    }

    public long getPlayerTimeOffset() {
        return playerTimeOffset;
    }

    public void setPlayerTimeOffset(long playerTimeOffset) {
        this.playerTimeOffset = playerTimeOffset;
    }

    public String getPlayerWeather() {
        return playerWeather;
    }

    public void setPlayerWeather(String playerWeather) {
        this.playerWeather = playerWeather;
    }

    public float getSaturation() {
        return saturation;
    }

    public void setSaturation(float saturation) {
        this.saturation = saturation;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public boolean isPickupItems() {
        return pickupItems;
    }

    public void setPickupItems(boolean pickupItems) {
        this.pickupItems = pickupItems;
    }

    public Location getCompassTarget() {
        return compassTarget;
    }

    public void setCompassTarget(Location compassTarget) {
        this.compassTarget = compassTarget;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public double getLastDamage() {
        return lastDamage;
    }

    public void setLastDamage(double lastDamage) {
        this.lastDamage = lastDamage;
    }

    public int getMaximumNoDamageTicks() {
        return maximumNoDamageTicks;
    }

    public void setMaximumNoDamageTicks(int maximumNoDamageTicks) {
        this.maximumNoDamageTicks = maximumNoDamageTicks;
    }

    public int getNoDamageTicks() {
        return noDamageTicks;
    }

    public void setNoDamageTicks(int noDamageTicks) {
        this.noDamageTicks = noDamageTicks;
    }

    public int getSleepTicks() {
        return sleepTicks;
    }

    public void setSleepTicks(int sleepTicks) {
        this.sleepTicks = sleepTicks;
    }

    public int getTicksLived() {
        return ticksLived;
    }

    public void setTicksLived(int ticksLived) {
        this.ticksLived = ticksLived;
    }

    public abstract T getHandle();

    public abstract boolean hasPermission(String permission);
}