package me.piggypiglet.framework.http.responses;

import com.google.inject.Singleton;
import fi.iki.elonen.NanoHTTPD;

import java.util.ArrayList;
import java.util.List;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
@Singleton
public final class ResponseHandler {
    private final List<Route> routes = new ArrayList<>();

    public NanoHTTPD.Response serve(NanoHTTPD.IHTTPSession session) {
        for (Route route : routes) {
            if (session.getUri().toLowerCase().replace("/", "").startsWith(route.getRoute())) {
                NanoHTTPD.Response response = NanoHTTPD.newFixedLengthResponse(route.run(session.getParameters()));
                response.addHeader("Content-Type", "application/json");
                return response;
            }
        }

        return NanoHTTPD.newFixedLengthResponse("null");
    }

    public List<Route> getRoutes() {
        return routes;
    }
}