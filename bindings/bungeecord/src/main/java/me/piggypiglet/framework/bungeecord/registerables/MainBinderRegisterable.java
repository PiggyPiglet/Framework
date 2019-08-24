package me.piggypiglet.framework.bungeecord.registerables;

import com.google.inject.Inject;
import me.piggypiglet.framework.registerables.StartupRegisterable;
import me.piggypiglet.framework.utils.annotations.Main;
import net.md_5.bungee.api.plugin.Plugin;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class MainBinderRegisterable extends StartupRegisterable {
    @Inject @Main private Object main;

    @Override
    protected void execute() {
        addBinding(Plugin.class, main);
    }
}
