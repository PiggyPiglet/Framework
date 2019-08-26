package me.piggypiglet.framework.velocity;

import me.piggypiglet.framework.utils.annotations.addon.Addon;
import me.piggypiglet.framework.utils.annotations.registerable.AddonStartup;
import me.piggypiglet.framework.velocity.registerables.CommandExecutorRegisterable;
import me.piggypiglet.framework.velocity.registerables.EventFinderRegisterable;
import me.piggypiglet.framework.velocity.registerables.LoggerRegisterable;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
@Addon(
        startup = {
                @AddonStartup(LoggerRegisterable.class),
                @AddonStartup(CommandExecutorRegisterable.class),
                @AddonStartup(EventFinderRegisterable.class)
        }
)
public final class VelocityAddon {
}
