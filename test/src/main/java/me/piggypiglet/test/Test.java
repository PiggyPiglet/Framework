package me.piggypiglet.test;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import me.piggypiglet.framework.Framework;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
@Plugin(
        id = "test", name = "test", version = "1.0", description = "test", authors = "PiggyPiglet"
)
public final class Test {
    @Inject private Injector injector;

    @Subscribe
    public void onEnable(ProxyInitializeEvent event) {
        Framework.builder()
                .main(this)
                .pckg("me.piggypiglet.test")
                .injector(injector)
                .commandPrefix("ploogin")
                .build()
                .init();
    }
}
