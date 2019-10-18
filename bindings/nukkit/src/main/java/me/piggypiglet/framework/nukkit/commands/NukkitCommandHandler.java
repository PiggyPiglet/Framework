/*
 * MIT License
 *
 * Copyright (c) 2019 PiggyPiglet
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

package me.piggypiglet.framework.nukkit.commands;

import me.piggypiglet.framework.commands.Command;
import me.piggypiglet.framework.commands.CommandHandler;
import me.piggypiglet.framework.nukkit.user.NukkitUser;
import me.piggypiglet.framework.user.User;

public final class NukkitCommandHandler extends CommandHandler {
    @Override
    protected boolean process(User user, Command command) {
        if (user instanceof NukkitUser && command instanceof NukkitCommand) {
            if (((NukkitCommand) command).isPlayerOnly()) {
                if (((NukkitUser) user).isPlayer()) {
                    return true;
                } else {
                    user.sendMessage("Only player's can execute this command.");
                    return false;
                }
            }
        }
        return true;
    }

}
