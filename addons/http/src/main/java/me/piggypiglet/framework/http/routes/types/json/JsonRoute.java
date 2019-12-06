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
import com.google.gson.GsonBuilder;
import me.piggypiglet.framework.http.routes.Route;
import me.piggypiglet.framework.http.routes.objects.Header;

import java.util.List;
import java.util.Map;

public abstract class JsonRoute extends Route {
    protected final Gson gson;

    static {
        HEADERS.add(new Header("Content-Type", "application/json"));
    }

    /**
     * Provide the path of this route, do not include a forward slash at the beginning.
     * @param route Route
     * @param headers Optional headers to append to the page
     */
    protected JsonRoute(String route, Header... headers) {
        this(route, new GsonBuilder().setPrettyPrinting().create(), headers);
    }

    protected JsonRoute(String route, Gson gson, Header... headers) {
        super(route, headers);
        this.gson = gson;
    }

    @Override
    public Object run(Map<String, List<String>> params, List<Header> headers, String ip) {
        return gson.toJson(super.run(params, headers, ip));
    }
}
