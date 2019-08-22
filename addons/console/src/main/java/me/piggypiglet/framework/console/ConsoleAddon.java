package me.piggypiglet.framework.console;

import me.piggypiglet.framework.console.registerables.ConsoleRegisterable;
import me.piggypiglet.framework.utils.annotations.addon.Addon;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
@Addon(startup = {ConsoleRegisterable.class})
public final class ConsoleAddon {
}
