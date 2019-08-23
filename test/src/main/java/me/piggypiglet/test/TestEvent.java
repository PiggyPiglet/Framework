package me.piggypiglet.test;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import me.piggypiglet.framework.logging.Logger;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class TestEvent {
    @Inject private Logger logger;

    @Subscribe
    public void onTest(ProxyShutdownEvent event) {
        logger.info("test1");
    }
}
