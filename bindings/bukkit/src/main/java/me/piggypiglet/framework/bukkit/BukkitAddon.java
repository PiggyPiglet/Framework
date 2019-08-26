package me.piggypiglet.framework.bukkit;

import me.piggypiglet.framework.bukkit.registerables.CommandExecutorRegisterable;
import me.piggypiglet.framework.bukkit.registerables.EventFinderRegisterable;
import me.piggypiglet.framework.bukkit.registerables.LoggerRegisterable;
import me.piggypiglet.framework.bukkit.registerables.MainBinderRegisterable;
import me.piggypiglet.framework.utils.annotations.addon.Addon;
import me.piggypiglet.framework.utils.annotations.registerable.AddonStartup;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
@Addon(startup = {
        @AddonStartup(LoggerRegisterable.class),
        @AddonStartup(MainBinderRegisterable.class),
        @AddonStartup(CommandExecutorRegisterable.class),
        @AddonStartup(EventFinderRegisterable.class)
})
public final class BukkitAddon {
}
