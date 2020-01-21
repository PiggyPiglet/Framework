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

package me.piggypiglet.framework.bungeecord.user;

import me.piggypiglet.framework.bungeecord.binding.player.BungeeCordPlayer;
import me.piggypiglet.framework.minecraft.player.Player;
import me.piggypiglet.framework.minecraft.user.MinecraftUser;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.chat.ComponentSerializer;

public final class BungeeUser extends MinecraftUser {
    private static final Class<? extends CommandSender> CONSOLE_COMMAND_SENDER;

    static {
        try {
            //noinspection unchecked
            CONSOLE_COMMAND_SENDER = (Class<? extends CommandSender>) Class.forName("net.md_5.bungee.command.ConsoleCommandSender");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private final CommandSender sender;

    public BungeeUser(CommandSender sender) {
        super(
                sender.getName(),
                sender instanceof ProxiedPlayer ? ((ProxiedPlayer) sender).getUniqueId().toString() : "Console"
        );

        this.sender = sender;
    }

    @Override
    protected void sendMessage(String message) {
        sender.sendMessage(TextComponent.fromLegacyText(message));
    }

    @Override
    protected void sendJsonMessage(String json) {
        sender.sendMessage(ComponentSerializer.parse(json));
    }

    @Override
    public boolean hasPermission(String permission) {
        return sender.hasPermission(permission);
    }

    @Override
    public boolean isPlayer() {
        return sender instanceof ProxiedPlayer;
    }

    //todo: test this
    @Override
    public boolean isConsole() {
        return sender.getClass().isAssignableFrom(CONSOLE_COMMAND_SENDER);
    }

    @Override
    public Player getAsPlayer() {
        return new BungeeCordPlayer((ProxiedPlayer) sender);
    }
}
