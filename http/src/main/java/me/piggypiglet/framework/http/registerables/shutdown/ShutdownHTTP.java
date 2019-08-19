package me.piggypiglet.framework.http.registerables.shutdown;

import com.google.inject.Inject;
import me.piggypiglet.framework.http.HTTPServer;
import me.piggypiglet.framework.registerables.ShutdownRegisterable;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class ShutdownHTTP extends ShutdownRegisterable {
    @Inject private HTTPServer httpServer;

    @Override
    protected void execute() {
        httpServer.stop();
    }
}
