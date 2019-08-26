package me.piggypiglet.framework.http;

import me.piggypiglet.framework.http.annotations.DefaultHTTP;
import me.piggypiglet.framework.http.annotations.HTTP;
import me.piggypiglet.framework.http.registerables.shutdown.ShutdownHTTP;
import me.piggypiglet.framework.http.registerables.startup.HTTPRegisterable;
import me.piggypiglet.framework.http.registerables.startup.RoutesRegisterable;
import me.piggypiglet.framework.utils.annotations.addon.Addon;
import me.piggypiglet.framework.utils.annotations.addon.File;
import me.piggypiglet.framework.utils.annotations.registerable.Startup;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
@Addon(
        startup = {
                @Startup(RoutesRegisterable.class),
                @Startup(HTTPRegisterable.class)
        },
        files = {@File(
                config = true,
                name = "http",
                externalPath = "./http.json",
                internalPath = "/http.json",
                annotation = HTTP.class
        ), @File(
                config = false,
                name = "http-default",
                externalPath = "./index.html",
                internalPath = "/index.html",
                annotation = DefaultHTTP.class
        )},
        shutdown = ShutdownHTTP.class
)
public final class HTTPAddon {}
