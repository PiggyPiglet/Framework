package me.piggypiglet.framework.bungeecord;

import me.piggypiglet.framework.bungeecord.registerables.CommandExecutorRegisterable;
import me.piggypiglet.framework.bungeecord.registerables.EventFinderRegisterable;
import me.piggypiglet.framework.bungeecord.registerables.LoggerRegisterable;
import me.piggypiglet.framework.utils.annotations.addon.Addon;
import me.piggypiglet.framework.utils.annotations.registerable.Startup;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
@Addon(
        startup = {
                @Startup(LoggerRegisterable.class),
                @Startup(EventFinderRegisterable.class),
                @Startup(CommandExecutorRegisterable.class)
        }
)
public final class BungeeAddon {
}
