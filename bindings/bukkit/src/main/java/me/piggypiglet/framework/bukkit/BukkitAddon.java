package me.piggypiglet.framework.bukkit;

import me.piggypiglet.framework.bukkit.registerables.CommandExecutorRegisterable;
import me.piggypiglet.framework.bukkit.registerables.EventFinderRegisterable;
import me.piggypiglet.framework.bukkit.registerables.LoggerRegisterable;
import me.piggypiglet.framework.bukkit.registerables.MainBinderRegisterable;
import me.piggypiglet.framework.utils.annotations.addon.Addon;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
@Addon(startup = {LoggerRegisterable.class, MainBinderRegisterable.class, CommandExecutorRegisterable.class, EventFinderRegisterable.class})
public final class BukkitAddon {
}
