package me.piggypiglet.framework.console;

import me.piggypiglet.framework.console.registerables.ConsoleRegisterable;
import me.piggypiglet.framework.utils.annotations.addon.Addon;
import me.piggypiglet.framework.utils.annotations.registerable.Startup;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
@Addon(startup = @Startup(ConsoleRegisterable.class))
public final class ConsoleAddon {
}
