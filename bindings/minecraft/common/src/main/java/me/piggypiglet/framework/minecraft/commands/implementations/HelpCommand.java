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

package me.piggypiglet.framework.minecraft.commands.implementations;

import com.google.inject.Inject;
import me.piggypiglet.framework.Framework;
import me.piggypiglet.framework.commands.CommandHandlers;
import me.piggypiglet.framework.commands.framework.Command;
import me.piggypiglet.framework.minecraft.commands.framework.MinecraftCommand;
import me.piggypiglet.framework.minecraft.user.MinecraftUser;
import me.piggypiglet.framework.user.User;
import me.piggypiglet.framework.utils.annotations.reflection.def.Default;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Default
public final class HelpCommand extends MinecraftCommand {
    @Inject private CommandHandlers commandHandlers;
    @Inject private Framework framework;

    public HelpCommand() {
        super("help");
        options()
                .description("Get a description of all commands or a specific one.")
                .usage("[command]")
                .def(true);
    }

    @Override
    protected boolean execute(@NotNull final MinecraftUser user, @NotNull final String[] args) {
        final Set<Command<? extends User, ?>> commands = commandHandlers.getAllCommands();
        final String prefix = Objects.requireNonNull(framework.getCommandPrefixes())[0];

        if (args.length > 0) {
            final Optional<Command<? extends User, ?>> command = commands.stream().filter(c -> c.getCommand().equalsIgnoreCase(args[0])).findAny();

            if (command.isPresent()) {
                final Command<? extends User, ?> cmd = command.get();
                user.sendMessage("&c/%s %s%s &8- &7%s\n",
                        prefix,
                        cmd.getCommand(),
                        cmd.getUsage().isEmpty() ? "" : " " + cmd.getUsage(),
                        cmd.getDescription());
            } else {
                user.sendMessage("&cThat command does not exist.");
            }

            return true;
        }

        final StringBuilder builder = new StringBuilder("&7Help menu\n");

        commands.forEach(c -> {
            builder.append("&c/").append(prefix).append(" ").append(c.getCommand());

            if (!c.getUsage().isEmpty()) {
                builder.append(" ").append(c.getUsage());
            }

            builder.append(" &8- &7").append(c.getDescription()).append("\n");
        });

        user.sendMessage(builder.toString());

        return true;
    }
}
