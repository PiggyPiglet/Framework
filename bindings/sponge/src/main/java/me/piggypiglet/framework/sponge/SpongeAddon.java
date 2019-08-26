package me.piggypiglet.framework.sponge;

import me.piggypiglet.framework.sponge.registerables.CommandExecutorRegisterable;
import me.piggypiglet.framework.sponge.registerables.EventFinderRegisterable;
import me.piggypiglet.framework.sponge.registerables.LoggerRegisterable;
import me.piggypiglet.framework.utils.annotations.addon.Addon;
import me.piggypiglet.framework.utils.annotations.registerable.Startup;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
@Addon(
        startup = {
                @Startup(LoggerRegisterable.class),
                @Startup(CommandExecutorRegisterable.class),
                @Startup(EventFinderRegisterable.class)
        }
)
public final class SpongeAddon {
}
