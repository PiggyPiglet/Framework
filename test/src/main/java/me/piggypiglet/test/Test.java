package me.piggypiglet.test;

import me.piggypiglet.framework.Framework;
import org.bukkit.plugin.java.JavaPlugin;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class Test extends JavaPlugin {
    @Override
    public void onEnable() {
        Framework.builder()
                .main(Test.class)
                .pckg("me.piggypiglet.test")
                .commandPrefix("!")
                .build()
                .init();
    }
}
