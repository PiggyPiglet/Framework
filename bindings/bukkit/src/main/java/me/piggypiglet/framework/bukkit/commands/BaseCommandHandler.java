package me.piggypiglet.framework.bukkit.commands;

import com.google.inject.Inject;
import me.piggypiglet.framework.Framework;
import me.piggypiglet.framework.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.SimplePluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class BaseCommandHandler {
    @Inject private Framework framework;
    @Inject private JavaPlugin main;
    @Inject private Logger logger;

    private CommandMap commandMap;

    public BaseCommandHandler() {
        final Server server = Bukkit.getServer();

        if (server.getPluginManager() instanceof SimplePluginManager) {
            try {
                Field f = SimplePluginManager.class.getDeclaredField("commandMap");
                f.setAccessible(true);

                commandMap = (CommandMap) f.get(server.getPluginManager());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            logger.error("PluginManager is not an instance of SimplePluginManager.");
        }
    }

    public void registerCommands() {
        final String command = framework.getCommandPrefix();

        registerCommand(command);
    }

    private void registerCommand(String... aliases) {
        PluginCommand command = getCommand(aliases[0]);

        command.setAliases(Arrays.asList(aliases));
        commandMap.register(main.getDescription().getName(), command);
    }

    private PluginCommand getCommand(String name) {
        PluginCommand command = null;

        try {
            Constructor<PluginCommand> c = PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
            c.setAccessible(true);

            command = c.newInstance(name, main);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return command;
    }
}
