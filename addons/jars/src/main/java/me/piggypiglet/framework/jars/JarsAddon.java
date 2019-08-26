package me.piggypiglet.framework.jars;

import me.piggypiglet.framework.jars.registerables.LoaderRegisterable;
import me.piggypiglet.framework.utils.annotations.addon.Addon;
import me.piggypiglet.framework.utils.annotations.registerable.AddonStartup;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
@Addon(
        startup = @AddonStartup(LoaderRegisterable.class)
)
public final class JarsAddon {
}
