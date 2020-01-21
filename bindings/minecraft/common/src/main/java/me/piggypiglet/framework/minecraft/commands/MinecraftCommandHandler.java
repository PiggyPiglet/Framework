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

package me.piggypiglet.framework.minecraft.commands;

import me.piggypiglet.framework.commands.CommandHandler;
import me.piggypiglet.framework.commands.framework.Command;
import me.piggypiglet.framework.minecraft.commands.framework.GenericMinecraftCommand;
import me.piggypiglet.framework.minecraft.lang.Lang;
import me.piggypiglet.framework.minecraft.user.MinecraftUser;
import me.piggypiglet.framework.user.User;

public class MinecraftCommandHandler extends CommandHandler {
    @Override
    protected boolean run(User user, Command command) {
        if (user instanceof MinecraftUser && command instanceof GenericMinecraftCommand) {
            final MinecraftUser mcUser = (MinecraftUser) user;
            final GenericMinecraftCommand<?> mcCommand = (GenericMinecraftCommand<?>) command;

            if (mcCommand.isPlayerOnly() && !mcUser.isPlayer()) {
                user.sendMessage(Lang.PLAYER_ONLY);
                return false;
            }

            if (mcCommand.isConsoleOnly() && !mcUser.isConsole()) {
                user.sendMessage(Lang.CONSOLE_ONLY);
                return false;
            }
        }

        if (command instanceof GenericMinecraftCommand && !(user instanceof MinecraftUser)) {
            user.sendMessage(Lang.NOT_MINECRAFT_USER);
            return false;
        }

        return super.run(user, command);
    }
}
