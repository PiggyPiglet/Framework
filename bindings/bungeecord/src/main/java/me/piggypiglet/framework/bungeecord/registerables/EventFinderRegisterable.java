package me.piggypiglet.framework.bungeecord.registerables;

import com.google.inject.Inject;
import me.piggypiglet.framework.reflection.Reflections;
import me.piggypiglet.framework.registerables.StartupRegisterable;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class EventFinderRegisterable extends StartupRegisterable {
    @Inject private Reflections reflections;
    @Inject private Plugin main;

    @Override
    protected void execute() {
        reflections.getSubTypesOf(Listener.class).stream().map(injector::getInstance).forEach(l -> main.getProxy().getPluginManager().registerListener(main, l));
    }
}
