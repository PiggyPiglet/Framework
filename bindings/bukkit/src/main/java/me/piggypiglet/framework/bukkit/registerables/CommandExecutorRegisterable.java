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

package me.piggypiglet.framework.bukkit.registerables;

import com.google.inject.Inject;
import me.piggypiglet.framework.Framework;
import me.piggypiglet.framework.access.AccessManager;
import me.piggypiglet.framework.bukkit.commands.BaseCommandHandler;
import me.piggypiglet.framework.bukkit.commands.BukkitCommandHandler;
import me.piggypiglet.framework.registerables.StartupRegisterable;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class CommandExecutorRegisterable extends StartupRegisterable {
    @Inject private BaseCommandHandler baseCommandHandler;
    @Inject private JavaPlugin main;
    @Inject private Framework framework;
    @Inject private BukkitCommandHandler commandHandler;
    @Inject private AccessManager accessManager;

    @Override
    protected void execute() {
        baseCommandHandler.registerCommands();
        //todo: do a proper null check, throw custom exception if null
        Objects.requireNonNull(main.getCommand(framework.getCommandPrefix())).setExecutor(commandHandler);
        accessManager.newHandler("bukkit", injector);
    }
}
