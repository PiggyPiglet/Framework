package me.piggypiglet.framework.http;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import fi.iki.elonen.NanoHTTPD;
import me.piggypiglet.framework.file.framework.FileConfiguration;
import me.piggypiglet.framework.http.annotations.HTTP;
import me.piggypiglet.framework.http.responses.ResponseHandler;
import me.piggypiglet.framework.logging.Logger;
import me.piggypiglet.framework.logging.LoggerFactory;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
@Singleton
public final class HTTPServer {
    @Inject @HTTP private FileConfiguration config;
    @Inject private ResponseHandler responseHandler;

    private static final Logger LOGGER = LoggerFactory.getLogger("HTTP");

    private NanoHTTPD nanoHTTPD;

    public void start() {
        String ip = config.getString("ip");
        int port = config.getInt("port");

        try {
            nanoHTTPD = new NestedServer(ip, port);
            nanoHTTPD.start();
            LOGGER.info("HTTP Server started at {}:{}", ip, port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        LOGGER.info("Shutting down HTTP server.");
        nanoHTTPD.stop();
    }

    private final class NestedServer extends NanoHTTPD {
        NestedServer(String ip, int port) {
            super(ip, port);
        }

        @Override
        public Response serve(IHTTPSession session) {
            return responseHandler.serve(session);
        }
    }
}