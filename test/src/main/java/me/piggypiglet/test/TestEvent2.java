package me.piggypiglet.test;

import com.google.inject.Inject;
import com.velocitypowered.api.event.EventHandler;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import me.piggypiglet.framework.logging.Logger;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class TestEvent2 implements EventHandler<ProxyShutdownEvent> {
    @Inject private Logger logger;

    @Override
    public void execute(ProxyShutdownEvent event) {
        logger.info("test2");
    }
}
