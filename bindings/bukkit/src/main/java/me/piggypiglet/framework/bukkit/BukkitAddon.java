package me.piggypiglet.framework.bukkit;

import me.piggypiglet.framework.bukkit.registerables.CommandExecutorRegisterable;
import me.piggypiglet.framework.bukkit.registerables.EventFinderRegisterable;
import me.piggypiglet.framework.bukkit.registerables.LoggerRegisterable;
import me.piggypiglet.framework.bukkit.registerables.MainBinderRegisterable;
import me.piggypiglet.framework.utils.annotations.addon.Addon;
import me.piggypiglet.framework.utils.annotations.registerable.Startup;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
@Addon(startup = {
        @Startup(LoggerRegisterable.class),
        @Startup(MainBinderRegisterable.class),
        @Startup(CommandExecutorRegisterable.class),
        @Startup(EventFinderRegisterable.class)
})
public final class BukkitAddon {
}
