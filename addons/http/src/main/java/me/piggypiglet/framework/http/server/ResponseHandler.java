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

package me.piggypiglet.framework.http.server;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import fi.iki.elonen.NanoHTTPD;
import me.piggypiglet.framework.addon.ConfigManager;
import me.piggypiglet.framework.file.objects.FileWrapper;
import me.piggypiglet.framework.http.HTTPAddon;
import me.piggypiglet.framework.http.files.DefaultHTTP;
import me.piggypiglet.framework.http.routes.Route;
import me.piggypiglet.framework.http.routes.auth.permissions.Permission;
import me.piggypiglet.framework.http.routes.mixins.Authenticated;
import me.piggypiglet.framework.http.routes.objects.Header;
import me.piggypiglet.framework.managers.Manager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Singleton
public final class ResponseHandler {
    @Inject @DefaultHTTP private FileWrapper def;
    @Inject private ConfigManager configManager;
    @Inject private Manager<Permission> permStore;

    private final List<Route> routes = new ArrayList<>();

    /**
     * Find the first route the matches the user's input, and handle the response
     * @param session Session info
     * @return Response
     */
    public NanoHTTPD.Response serve(NanoHTTPD.IHTTPSession session) {
        for (Route route : routes) {
            final String uri = session.getUri().toLowerCase().substring(1);

            if (route.getRoute().test(uri)) {
                final String sanitisedUri = route.getSanitiser().apply(uri);

                if (!sanitisedUri.isEmpty() && !sanitisedUri.equals("/") && !sanitisedUri.startsWith("?")) continue;

                final Authenticated auth = route.getClass().getAnnotation(Authenticated.class);

                if (auth != null) {
                    final NanoHTTPD.Response noPermission = NanoHTTPD.newFixedLengthResponse("<p>No permission.</p>");
                    final Map<String, Object> config = configManager.getConfigs().get(HTTPAddon.class).getItems();
                    final String token = session.getHeaders().getOrDefault("auth", null);

                    if (token == null || !permStore.exists(token)) return noPermission;

                    final Collection<String> perms = permStore.get(token).getPermissions();

                    if ((boolean) config.get("standard-authentication.enabled") &&
                            (!auth.permission().isEmpty() && !perms.contains(auth.permission()) && !perms.contains("*"))) {
                        return noPermission;
                    }
                }

                NanoHTTPD.Response response = NanoHTTPD.newFixedLengthResponse(route.run(
                        uri,
                        session.getParameters(),
                        session.getHeaders().entrySet().stream().map(e -> new Header(e.getKey(), e.getValue())).collect(Collectors.toList()),
                        session.getRemoteIpAddress()
                ).toString());
                route.getHeaders().forEach(h -> response.addHeader(h.getKey(), h.getValue()));
                return response;
            }
        }

        return NanoHTTPD.newFixedLengthResponse(this.def.getFileContent());
    }

    /**
     * Get all routes stored in this ResponseHandler
     * @return List of Routes
     */
    public List<Route> getRoutes() {
        return routes;
    }
}