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
    /**
     * GSON instance for returning json
     */
    protected final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private final String route;

    /**
     * Provide the path of this route, do not include a forward slash at the beginning.
     * @param route Route
     */
    protected Route(String route) {
        this.route = route;
    }

    /**
     * Process user-inputted params and return a string response
     * @param params User input
     * @return String
     */
    protected abstract String provide(Map<String, List<String>> params);

    /**
     * Process parameters and return a string based on them &amp; the route
     * @param params Params
     * @return String
     */
    public String run(Map<String, List<String>> params) {
        return provide(params);
    }

    /**
     * Get the value of the route defined in this route
     * @return String
     */
    public String getRoute() {
        return route;
    }
}