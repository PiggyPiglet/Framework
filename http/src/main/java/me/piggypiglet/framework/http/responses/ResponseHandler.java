package me.piggypiglet.framework.http.responses;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import fi.iki.elonen.NanoHTTPD;
import me.piggypiglet.framework.file.objects.FileWrapper;
import me.piggypiglet.framework.http.annotations.DefaultHTTP;

import java.util.ArrayList;
import java.util.List;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
@Singleton
public final class ResponseHandler {
    @Inject @DefaultHTTP private FileWrapper def;

    private final List<Route> routes = new ArrayList<>();

    public NanoHTTPD.Response serve(NanoHTTPD.IHTTPSession session) {
        for (Route route : routes) {
            if (session.getUri().toLowerCase().replace("/", "").startsWith(route.getRoute())) {
                NanoHTTPD.Response response = NanoHTTPD.newFixedLengthResponse(route.run(session.getParameters()));
                response.addHeader("Content-Type", "application/json");
                return response;
            }
        }

        return NanoHTTPD.newFixedLengthResponse(def.getFileContent());
    }

    public List<Route> getRoutes() {
        return routes;
    }
}