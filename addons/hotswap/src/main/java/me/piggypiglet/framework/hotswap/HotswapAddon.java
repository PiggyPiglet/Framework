package me.piggypiglet.framework.hotswap;

import me.piggypiglet.framework.hotswap.registerables.HotswapRegisterable;
import me.piggypiglet.framework.utils.annotations.addon.Addon;
import me.piggypiglet.framework.utils.annotations.registerable.AddonStartup;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
@Addon(startup = @AddonStartup(HotswapRegisterable.class))
public final class HotswapAddon {
}
