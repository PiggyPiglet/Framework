package me.piggypiglet.framework.jars;

import me.piggypiglet.framework.bootstrap.BootPriority;
import me.piggypiglet.framework.jars.registerables.ClassLoaderRegisterable;
import me.piggypiglet.framework.jars.registerables.LoaderFinderRegisterable;
import me.piggypiglet.framework.utils.annotations.addon.Addon;
import me.piggypiglet.framework.utils.annotations.registerable.Startup;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
@Addon(
        startup = {@Startup(
                value = ClassLoaderRegisterable.class,
                priority = BootPriority.AFTER_IMPL
        ),
        @Startup(
                value = LoaderFinderRegisterable.class,
                priority = BootPriority.AFTER_IMPL
        )}
)
public final class JarsAddon {
}
