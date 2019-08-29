package me.piggypiglet.demo;

import me.piggypiglet.demo.registerables.BroadcasterRegisterable;
import me.piggypiglet.framework.Framework;
import me.piggypiglet.framework.utils.annotations.files.Config;
import me.piggypiglet.framework.utils.annotations.registerable.RegisterableData;
import org.bukkit.plugin.java.JavaPlugin;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class Demo extends JavaPlugin {
    @Override
    public void onEnable() {
        Framework.builder()
                .main(JavaPlugin.class, this)
                .pckg("me.piggypiglet.demo")
                .commandPrefix("demo")
                .startup(new RegisterableData(BroadcasterRegisterable.class))
                .file(true, "config", "/config.yml", getDataFolder() + "/config.yml", Config.class)
                .build()
                .init();
    }
}
