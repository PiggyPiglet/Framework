package me.piggypiglet.test;

import me.piggypiglet.framework.Framework;
import org.bukkit.plugin.java.JavaPlugin;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class TestPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        Framework.builder()
                .main(getClass())
                .pckg("me.piggypiglet.test")
                .commandPrefix("test")
                .build()
                .init();
    }
}
