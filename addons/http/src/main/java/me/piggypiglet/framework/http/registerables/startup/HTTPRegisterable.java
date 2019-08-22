package me.piggypiglet.framework.http.registerables.startup;

import com.google.inject.Inject;
import me.piggypiglet.framework.http.HTTPServer;
import me.piggypiglet.framework.registerables.StartupRegisterable;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class HTTPRegisterable extends StartupRegisterable {
    @Inject private HTTPServer httpServer;

    @Override
    protected void execute() {
        httpServer.start();
    }
}
