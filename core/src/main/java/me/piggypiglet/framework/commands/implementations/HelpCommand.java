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

package me.piggypiglet.framework.commands.implementations;

import com.google.inject.Inject;
import me.piggypiglet.framework.Framework;
import me.piggypiglet.framework.commands.Command;
import me.piggypiglet.framework.commands.CommandHandlers;
import me.piggypiglet.framework.user.User;
import me.piggypiglet.framework.utils.annotations.reflection.def.UltraDefault;

@UltraDefault
public final class HelpCommand extends Command {
    @Inject private CommandHandlers commandHandlers;
    @Inject private Framework framework;

    protected HelpCommand() {
        super("help");
        options.description("Help command.").usage("");
    }

    @Override
    protected boolean execute(User user, String[] args) {
        StringBuilder message = new StringBuilder("\n");

        commandHandlers.getCommands().forEach(c -> {
                    message.append(framework.getCommandPrefix()).append(c.getCommand());

                    if (!c.getUsage().isEmpty()) {
                        message.append(" ").append(c.getUsage());
                    }

                    message.append(" - ").append(c.getDescription()).append("\n");
                });

        user.sendMessage(message.toString());
        return true;
    }
}
