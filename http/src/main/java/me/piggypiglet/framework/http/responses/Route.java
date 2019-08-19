package me.piggypiglet.framework.http.responses;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.Map;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public abstract class Route {
    protected final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private final String route;

    protected Route(String route) {
        this.route = route;
    }

    protected abstract String provide(Map<String, List<String>> params);

    public String run(Map<String, List<String>> params) {
        return provide(params);
    }

    public String getRoute() {
        return route;
    }
}