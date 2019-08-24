package me.piggypiglet.framework.bungeecord;

import me.piggypiglet.framework.bungeecord.registerables.CommandExecutorRegisterable;
import me.piggypiglet.framework.bungeecord.registerables.EventFinderRegisterable;
import me.piggypiglet.framework.bungeecord.registerables.LoggerRegisterable;
import me.piggypiglet.framework.bungeecord.registerables.MainBinderRegisterable;
import me.piggypiglet.framework.utils.annotations.addon.Addon;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
@Addon(
        startup = {MainBinderRegisterable.class, LoggerRegisterable.class, EventFinderRegisterable.class, CommandExecutorRegisterable.class}
)
public final class BungeeAddon {
}
