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

package me.piggypiglet.framework.http.responses.routes;

import me.piggypiglet.framework.http.responses.routes.objects.Header;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public abstract class Route {
    protected static final List<Header> HEADERS = new ArrayList<>();

    private final String route;

    protected String requestIp;

    /**
     * Provide the path of this route, do not include a forward slash at the beginning.
     * @param route Route
     * @param HEADERS Optional headers to append to the page
     */
    protected Route(String route, Header... HEADERS) {
        this.route = route;
        Route.HEADERS.addAll(Arrays.asList(HEADERS));
    }

    /**
     * Process user-inputted params and return a string response
     * @param params Query parameters on the URL
     * @param headers Any headers that were supplied in the request
     * @return Object, will be converted to string
     */
    protected abstract Object provide(Map<String, List<String>> params, List<Header> headers);

    /**
     * Process parameters and return a string based on them &amp; the route
     * @param params Map of params and their value(s). Identical to a multimap, example format would be:
     *               url/route?test=oof&amp;test=oof2&amp;oof=test
     *               which would result in:
     *               {test:["oof", "oof2"],oof:["test"]}
     * @return Object, will be converted to string
     */
    public Object run(Map<String, List<String>> params, List<Header> headers, String ip) {
        this.requestIp = ip == null ? "127.0.0.1" : ip;
        return provide(params, headers);
    }

    /**
     * Get the value of the route defined in this route
     * @return String
     */
    public String getRoute() {
        return route;
    }

    /**
     * Get headers that this route should declare
     * @return List of headers
     */
    public List<Header> getHeaders() {
        return HEADERS;
    }
}