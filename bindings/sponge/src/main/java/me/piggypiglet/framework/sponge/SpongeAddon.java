package me.piggypiglet.framework.sponge;

import me.piggypiglet.framework.sponge.registerables.CommandExecutorRegisterable;
import me.piggypiglet.framework.sponge.registerables.EventFinderRegisterable;
import me.piggypiglet.framework.sponge.registerables.LoggerRegisterable;
import me.piggypiglet.framework.utils.annotations.addon.Addon;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
@Addon(
        startup = {LoggerRegisterable.class, CommandExecutorRegisterable.class, EventFinderRegisterable.class}
)
public final class SpongeAddon {
}
