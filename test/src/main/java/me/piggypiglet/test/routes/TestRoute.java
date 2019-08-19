package me.piggypiglet.test.routes;

import me.piggypiglet.framework.http.responses.Route;

import java.util.List;
import java.util.Map;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class TestRoute extends Route {
    public TestRoute() {
        super("test");
    }

    @Override
    protected String provide(Map<String, List<String>> params) {
        return gson.toJson(new Test("test"));
    }

    private class Test {
        private final String test;

        private Test(String test) {
            this.test = test;
        }

        private String getTest() {
            return test;
        }
    }
}
