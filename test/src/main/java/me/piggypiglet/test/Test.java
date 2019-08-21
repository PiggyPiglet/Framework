package me.piggypiglet.test;

import com.google.inject.Inject;
import com.google.inject.Injector;
import me.piggypiglet.framework.Framework;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePostInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.plugin.Plugin;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
@Plugin(
        id = "test", name = "test", version = "1.0", description = "test"
)
public final class Test {
    @Inject private Injector injector;

    @Listener
    public void onEnable(GamePreInitializationEvent event) {
        Framework.builder()
                .main(this)
                .pckg("me.piggypiglet.test")
                .injector(injector)
                .commandPrefix("ploogin")
                .build()
                .init();
    }
}
