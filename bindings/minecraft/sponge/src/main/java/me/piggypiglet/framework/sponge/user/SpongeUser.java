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

package me.piggypiglet.framework.sponge.user;

import me.piggypiglet.framework.minecraft.player.Player;
import me.piggypiglet.framework.minecraft.user.MinecraftUser;
import me.piggypiglet.framework.sponge.binding.player.SpongePlayer;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.text.serializer.TextSerializers;

public final class SpongeUser extends MinecraftUser {
    private final CommandSource src;

    public SpongeUser(CommandSource src) {
        super(
                src.getName(),
                src.getFriendlyIdentifier().orElse("null")
        );

        this.src = src;
    }

    @Override
    protected void sendMessage(String message) {
        src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(message));
    }

    @Override
    public boolean hasPermission(String permission) {
        return src.hasPermission(permission);
    }

    @Override
    public void sendJsonMessage(String json) {
        src.sendMessage(TextSerializers.JSON.deserialize(json));
    }

    @Override
    public boolean isPlayer() {
        return src instanceof org.spongepowered.api.entity.living.player.Player;
    }

    @Override
    public boolean isConsole() {
        return src instanceof ConsoleSource;
    }

    @Override
    public Player getAsPlayer() {
        return new SpongePlayer((org.spongepowered.api.entity.living.player.Player) src);
    }

    public CommandSource getSrc() {
        return src;
    }
}
