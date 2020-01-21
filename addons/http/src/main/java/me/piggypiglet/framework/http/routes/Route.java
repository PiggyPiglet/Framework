/*
 * MIT License
 *
 * Copyright (c) 2019-2020 PiggyPiglet
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

package me.piggypiglet.framework.http.routes;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import me.piggypiglet.framework.http.routes.objects.Header;
import me.piggypiglet.framework.http.routes.objects.Response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public abstract class Route {
    protected final List<Header> headers = new ArrayList<>();

    private final Predicate<String> route;
    private final UnaryOperator<String> sanitiser;

    /**
     * Provide the path of this route, do not include a forward slash at the beginning.
     *
     * @param route   Route (lowercase necessary)
     * @param headers Optional headers to append to the page
     */
    protected Route(String route, Header... headers) {
        this(uri -> uri.startsWith(route), uri -> uri.replace(route, ""), headers);
    }

    /**
     * Provide the direct predicate the URI will be tested against to see if this
     * route object is a match.
     *
     * @param route     Route
     * @param sanitiser URI sanitiser (Remove the route from the URI)
     * @param headers   Optional headers to append to the page
     */
    protected Route(Predicate<String> route, UnaryOperator<String> sanitiser, Header... headers) {
        this.route = route;
        this.sanitiser = sanitiser;
        this.headers.addAll(Arrays.asList(headers));
    }

    /**
     * Process user data and return a response, which will
     * be converted to a string.
     *
     * @param response The client response, containing all
     *                 relevant data.
     * @return Object, will be converted to string
     */
    protected abstract Object provide(Response response);

    /**
     * Process parameters and return a string based on them &amp; the route
     *
     * @param uri     Accessed URI
     * @param params  Map of params and their value(s). Identical to a multimap, example format would be:
     *                url/route?test=oof&amp;test=oof2&amp;oof=test
     *                which would result in:
     *                {test:["oof", "oof2"],oof:["test"]}
     * @param headers List of headers
     * @param ip      Client ip
     * @return Object, will be converted to string
     */
    public Object run(String uri, Map<String, List<String>> params, List<Header> headers, String ip) {
        final Multimap<String, String> mappedParams = ArrayListMultimap.create();
        params.forEach(mappedParams::putAll);

        return provide(new Response(uri, mappedParams, headers, ip));
    }

    /**
     * Get the logic to check if a URI matches this route
     *
     * @return String Predicate
     */
    public Predicate<String> getRoute() {
        return route;
    }

    /**
     * Get the logic to sanitise the URI (i.e. remove the
     * hard route from the URI string)
     *
     * @return String Unary Operator
     */
    public UnaryOperator<String> getSanitiser() {
        return sanitiser;
    }

    /**
     * Get headers that this route should declare
     *
     * @return List of headers
     */
    public List<Header> getHeaders() {
        return headers;
    }
}