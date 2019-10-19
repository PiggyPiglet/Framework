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

package me.piggypiglet.framework.nukkit.registerables;

import cn.nukkit.command.PluginCommand;
import cn.nukkit.plugin.PluginBase;
import com.google.inject.Inject;
import me.piggypiglet.framework.Framework;
import me.piggypiglet.framework.commands.CommandHandlers;
import me.piggypiglet.framework.guice.objects.MainBinding;
import me.piggypiglet.framework.nukkit.commands.NukkitCommandHandler;
import me.piggypiglet.framework.nukkit.commands.nukkit.BaseCommandHandler;
import me.piggypiglet.framework.nukkit.commands.nukkit.NukkitCommandExecutor;
import me.piggypiglet.framework.registerables.StartupRegisterable;

//todo Figure out the issue here. It seems like you don't actually have to register commands in Nukkit
public final class CommandExecutorRegisterable extends StartupRegisterable {
    @Inject private BaseCommandHandler baseCommandHandler;
    @Inject private MainBinding main;
    @Inject private Framework framework;
    @Inject private NukkitCommandExecutor commandExecutor;
    @Inject private CommandHandlers commandHandlers;

    @Override
    protected void execute() {
        baseCommandHandler.registerCommands();
        //todo: do a proper null check, throw custom exception if null
        ((PluginCommand) ((PluginBase) main.getInstance()).getCommand(framework.getCommandPrefix())).setExecutor(commandExecutor);
        commandHandlers.newHandler("nukkit", NukkitCommandHandler.class, injector);
    }
}
