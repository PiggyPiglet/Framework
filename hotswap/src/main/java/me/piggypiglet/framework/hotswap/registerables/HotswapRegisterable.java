package me.piggypiglet.framework.hotswap.registerables;

import me.piggypiglet.framework.hotswap.plugin.HotswapPlugin;
import me.piggypiglet.framework.registerables.StartupRegisterable;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class HotswapRegisterable extends StartupRegisterable {
    @Override
    protected void execute() {
        requestStaticInjections(HotswapPlugin.class);
    }
}
