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

package me.piggypiglet.framework.bungeecord.registerables;

import com.google.inject.Inject;
import me.piggypiglet.framework.bungeecord.commands.BungeeCommandExecutor;
import me.piggypiglet.framework.commands.CommandHandlers;
import me.piggypiglet.framework.guice.objects.MainBinding;
import me.piggypiglet.framework.registerables.StartupRegisterable;
import net.md_5.bungee.api.plugin.Plugin;

public final class CommandExecutorRegisterable extends StartupRegisterable {
    @Inject private CommandHandlers commandHandlers;
    @Inject private MainBinding main;
    @Inject private BungeeCommandExecutor commandExecutor;

    @Override
    protected void execute() {
        final Plugin plugin = (Plugin) main.getInstance();

        commandHandlers.newHandler("bungee", injector);
        plugin.getProxy().getPluginManager().registerCommand(plugin, commandExecutor.getExecutor());
    }
}