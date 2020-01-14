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

package me.piggypiglet.framework.http.routes.objects;

import com.google.common.collect.Multimap;

import java.util.List;

// ------------------------------
// Copyright (c) PiggyPiglet 2020
// https://www.piggypiglet.me
// ------------------------------
public final class Response {
    private final String uri;
    private final Multimap<String, String> params;
    private final List<Header> headers;
    private final String ip;

    public Response(String uri, Multimap<String, String> params, List<Header> headers, String ip) {
        this.uri = uri;
        this.params = params;
        this.headers = headers;
        this.ip = ip;
    }

    public String getUri() {
        return uri;
    }

    public Multimap<String, String> getParams() {
        return params;
    }

    public List<Header> getHeaders() {
        return headers;
    }

    public String getIp() {
        return ip;
    }

    @Override
    public String toString() {
        return "Response{" +
                "uri='" + uri + '\'' +
                ", params=" + params +
                ", headers=" + headers +
                ", ip='" + ip + '\'' +
                '}';
    }
}
