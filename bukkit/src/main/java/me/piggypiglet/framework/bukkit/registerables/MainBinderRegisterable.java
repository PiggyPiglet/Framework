package me.piggypiglet.framework.bukkit.registerables;

import com.google.inject.Inject;
import me.piggypiglet.framework.Framework;
import me.piggypiglet.framework.logging.LoggerFactory;
import me.piggypiglet.framework.registerables.StartupRegisterable;
import org.bukkit.plugin.java.JavaPlugin;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class MainBinderRegisterable extends StartupRegisterable {
    @Inject private Framework framework;

    @Override
    protected void execute() {
        LoggerFactory.getLogger("Bukkit").info("Initializing Bukkit bindings.");
        addBinding(JavaPlugin.class, JavaPlugin.getProvidingPlugin(framework.getMain()));
    }
}
