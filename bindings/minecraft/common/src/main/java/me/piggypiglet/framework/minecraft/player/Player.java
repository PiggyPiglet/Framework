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