package me.piggypiglet.test;

import com.google.inject.Inject;
import me.piggypiglet.framework.logging.Logger;
import org.spongepowered.api.event.EventListener;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class TestEvent implements EventListener<GameStartedServerEvent> {
    @Inject private Logger logger;

    @Override
    public void handle(GameStartedServerEvent event) throws Exception {
        logger.info("test");
    }
}
