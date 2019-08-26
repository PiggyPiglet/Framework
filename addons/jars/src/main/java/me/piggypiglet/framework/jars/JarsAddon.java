package me.piggypiglet.framework.jars;

import me.piggypiglet.framework.bootstrap.BootPriority;
import me.piggypiglet.framework.jars.registerables.LoaderRegisterable;
import me.piggypiglet.framework.utils.annotations.addon.Addon;
import me.piggypiglet.framework.utils.annotations.registerable.Startup;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
@Addon(
        startup = @Startup(
                value = LoaderRegisterable.class,
                priority = BootPriority.BEFORE_IMPL
        )
)
public final class JarsAddon {
}
