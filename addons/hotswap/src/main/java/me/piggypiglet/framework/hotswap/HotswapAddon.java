package me.piggypiglet.framework.hotswap;

import me.piggypiglet.framework.hotswap.registerables.HotswapRegisterable;
import me.piggypiglet.framework.utils.annotations.addon.Addon;
import me.piggypiglet.framework.utils.annotations.registerable.Startup;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
@Addon(startup = @Startup(HotswapRegisterable.class))
public final class HotswapAddon {
}
