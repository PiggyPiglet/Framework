package me.piggypiglet.framework.http.responses.routes.types.json;

import me.piggypiglet.framework.http.responses.routes.mixins.json.manager.Removable;
import me.piggypiglet.framework.http.responses.routes.objects.Header;
import me.piggypiglet.framework.managers.implementations.SearchableManager;
import me.piggypiglet.framework.utils.SearchUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class JsonManagerRoute<T extends SearchUtils.Searchable> extends JsonRoute {
    private final SearchableManager<T> manager;

    private int searchResults = 1;

    protected Options options = new Options();

    protected JsonManagerRoute(String route, SearchableManager<T> manager, Header... headers) {
        super(route, headers);
        this.manager = manager;
    }

    @Override
    public Object run(Map<String, List<String>> params, List<Header> headers) {
        if (params.size() > 0) {
            final Map.Entry<String, List<String>> entry = params.entrySet().iterator().next();
            final String key = entry.getKey();
            final String value = entry.getValue().get(0);

            Object obj = null;

            switch (key) {
                case "get":
                    if (value.equals("all")) {
                        obj = manager.getAll();
                        break;
                    }

                    obj = manager.get(value);
                    break;

                case "search":
                    obj = manager.search(value).stream().limit(searchResults).collect(Collectors.toList());
                    break;

                case "exists":
                    obj = manager.exists(value);
                    break;

                case "remove":
                    if (getClass().isAnnotationPresent(Removable.class)) {
                        if (manager.exists(value)) {
                            manager.remove(manager.get(value));
                            obj = true;
                            break;
                        }

                        obj = false;
                    }
                    break;
            }

            if (obj != null) {
                return gson.toJson(obj);
            }

        }

        return provide(params, headers);
    }

    protected class Options {
        public Options searchResults(int searchResults) {
            JsonManagerRoute.this.searchResults = searchResults;
            return this;
        }
    }
}