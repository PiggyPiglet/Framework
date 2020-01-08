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
}
