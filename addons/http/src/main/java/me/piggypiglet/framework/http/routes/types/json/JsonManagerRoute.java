/*
 * MIT License
 *
 * Copyright (c) 2019 PiggyPiglet
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package me.piggypiglet.framework.http.routes.types.json;

import com.google.gson.Gson;
import me.piggypiglet.framework.http.routes.mixins.json.manager.Removable;
import me.piggypiglet.framework.http.routes.objects.Header;
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

    protected JsonManagerRoute(String route, SearchableManager<T> manager, Gson gson, Header... headers) {
        super(route, gson, headers);
        this.manager = manager;
    }

    @Override
    public Object run(Map<String, List<String>> params, List<Header> headers, String ip) {
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

        return provide(params, headers, ip);
    }

    protected class Options {
        public Options searchResults(int searchResults) {
            JsonManagerRoute.this.searchResults = searchResults;
            return this;
        }
    }
}