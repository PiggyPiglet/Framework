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

package me.piggypiglet.framework.velocity.user;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.ConsoleCommandSource;
import com.velocitypowered.api.proxy.Player;
import me.piggypiglet.framework.minecraft.user.MinecraftUser;
import net.kyori.text.serializer.gson.GsonComponentSerializer;
import net.kyori.text.serializer.legacy.LegacyComponentSerializer;

public final class VelocityUser extends MinecraftUser {
    private final CommandSource source;

    public VelocityUser(CommandSource source) {
        super(
                source instanceof ConsoleCommandSource ? "Console" : ((Player) source).getUsername(),
                source instanceof ConsoleCommandSource ? "Console" : ((Player) source).getUniqueId().toString()
        );

        this.source = source;
    }

    @Override
    protected void sendMessage(String message) {
        source.sendMessage(LegacyComponentSerializer.legacy().deserialize(message));
    }

    @Override
    public boolean hasPermission(String permission) {
        return source.hasPermission(permission);
    }

    @Override
    public void sendRawMessage(String json) {
        source.sendMessage(GsonComponentSerializer.INSTANCE.deserialize(json));
    }

    @Override
    public boolean isPlayer() {
        return source instanceof Player;
    }

    @Override
    public boolean isConsole() {
        return source instanceof ConsoleCommandSource;
    }

    @Override
    public me.piggypiglet.framework.minecraft.player.Player getAsPlayer() {
        return null;
    }
}
