package me.piggypiglet.framework.http;

import me.piggypiglet.framework.http.annotations.HTTP;
import me.piggypiglet.framework.http.registerables.shutdown.ShutdownHTTP;
import me.piggypiglet.framework.http.registerables.startup.HTTPRegisterable;
import me.piggypiglet.framework.http.registerables.startup.RoutesRegisterable;
import me.piggypiglet.framework.utils.annotations.addon.Addon;
import me.piggypiglet.framework.utils.annotations.addon.File;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
@Addon(
        startup = {RoutesRegisterable.class, HTTPRegisterable.class},
        files = {@File(
                config = true,
                name = "http",
                externalPath = "./http.json",
                internalPath = "/http.json",
                annotation = HTTP.class
        )},
        shutdown = ShutdownHTTP.class
)
public final class HTTPAddon {}
