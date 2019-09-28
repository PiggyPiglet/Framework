package me.piggypiglet.framework.http.responses.routes.types;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.piggypiglet.framework.http.responses.routes.Route;
import me.piggypiglet.framework.http.responses.routes.objects.Header;

import java.util.List;
import java.util.Map;

public abstract class JsonRoute extends Route {
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    static {
        HEADERS.add(new Header("Content-Type", "application/json"));
    }

    /**
     * Provide the path of this route, do not include a forward slash at the beginning.
     * @param route Route
     * @param headers Optional headers to append to the page
     */
    protected JsonRoute(String route, Header... headers) {
        super(route, headers);
    }

    @Override
    public Object run(Map<String, List<String>> params) {
        return gson.toJson(super.run(params));
    }
}
