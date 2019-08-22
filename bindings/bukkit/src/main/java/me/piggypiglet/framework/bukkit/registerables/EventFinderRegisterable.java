package me.piggypiglet.framework.bukkit.registerables;

import com.google.inject.Inject;
import me.piggypiglet.framework.reflection.Reflections;
import me.piggypiglet.framework.registerables.StartupRegisterable;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class EventFinderRegisterable extends StartupRegisterable {
    @Inject private Reflections reflections;
    @Inject private JavaPlugin main;

    @Override
    protected void execute() {
        reflections.getSubTypesOf(Listener.class).stream().map(injector::getInstance).forEach(l -> main.getServer().getPluginManager().registerEvents(l, main));
    }
}
