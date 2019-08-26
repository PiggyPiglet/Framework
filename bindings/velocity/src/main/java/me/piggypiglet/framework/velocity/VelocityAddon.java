package me.piggypiglet.framework.velocity;

import me.piggypiglet.framework.utils.annotations.addon.Addon;
import me.piggypiglet.framework.utils.annotations.registerable.Startup;
import me.piggypiglet.framework.velocity.registerables.CommandExecutorRegisterable;
import me.piggypiglet.framework.velocity.registerables.EventFinderRegisterable;
import me.piggypiglet.framework.velocity.registerables.LoggerRegisterable;

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
public final class VelocityAddon {
}
