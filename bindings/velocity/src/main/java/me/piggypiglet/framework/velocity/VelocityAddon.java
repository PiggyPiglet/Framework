package me.piggypiglet.framework.velocity;

import me.piggypiglet.framework.utils.annotations.addon.Addon;
import me.piggypiglet.framework.velocity.registerables.CommandExecutorRegisterable;
import me.piggypiglet.framework.velocity.registerables.LoggerRegisterable;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
@Addon(
        startup = {LoggerRegisterable.class, CommandExecutorRegisterable.class}
)
public final class VelocityAddon {
}
