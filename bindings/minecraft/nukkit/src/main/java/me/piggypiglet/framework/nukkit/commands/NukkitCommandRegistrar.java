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

package me.piggypiglet.framework.nukkit.commands;

import cn.nukkit.Server;
import cn.nukkit.command.CommandMap;
import cn.nukkit.command.PluginCommand;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.plugin.PluginManager;
import com.google.inject.Inject;
import me.piggypiglet.framework.Framework;
import me.piggypiglet.framework.guice.objects.MainBinding;
import me.piggypiglet.framework.logging.framework.Logger;
import me.piggypiglet.framework.utils.ReflectionUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

public class NukkitCommandRegistrar {
    @Inject private Framework framework;
    @Inject private MainBinding main;
    @Inject private Logger logger;

    private CommandMap commandMap;

    public NukkitCommandRegistrar() {
        final Server server = Server.getInstance();

        if (server.getPluginManager() != null) {
            try {
                final Field f = ReflectionUtils.getAccessible(PluginManager.class.getDeclaredField("commandMap"));
                commandMap = (CommandMap) f.get(server.getPluginManager());
            } catch (Exception e) {
                logger.error(e);
            }
        } else {
            logger.debug("PluginManager is null.");
        }
    }

    public void registerCommands() {
        registerCommand(framework.getCommandPrefixes());
    }

    private void registerCommand(String... aliases) {
        PluginCommand command = getCommand(aliases[0]);

        if (command == null) return;

        command.setAliases(aliases);
        commandMap.register(((PluginBase) main.getInstance()).getDescription().getName(), command);
    }

    private PluginCommand getCommand(String name) {
        PluginCommand command = null;

        try {
            final Constructor<PluginCommand> c = ReflectionUtils.getAccessible(PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class));
            command = c.newInstance(name, main.getInstance());
        } catch (Exception e) {
            logger.error(e);
        }

        return command;
    }
}
