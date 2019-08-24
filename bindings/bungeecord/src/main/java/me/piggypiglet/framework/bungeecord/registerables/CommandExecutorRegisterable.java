package me.piggypiglet.framework.bungeecord.registerables;

import com.google.inject.Inject;
import me.piggypiglet.framework.access.AccessManager;
import me.piggypiglet.framework.bungeecord.commands.BungeeCommandExecutor;
import me.piggypiglet.framework.registerables.StartupRegisterable;
import net.md_5.bungee.api.plugin.Plugin;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class CommandExecutorRegisterable extends StartupRegisterable {
    @Inject private AccessManager accessManager;
    @Inject private Plugin plugin;
    @Inject private BungeeCommandExecutor commandExecutor;

    @Override
    protected void execute() {
        accessManager.newHandler("bungee", injector);
        plugin.getProxy().getPluginManager().registerCommand(plugin, commandExecutor.getExecutor());
    }
}
