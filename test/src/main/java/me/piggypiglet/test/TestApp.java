package me.piggypiglet.test;

import me.piggypiglet.framework.Framework;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class TestApp {
    private TestApp() {
        Framework.builder()
                .pckg("me.piggypiglet.test")
                .main(this)
                .commandPrefix("!")
                .build()
                .init();
    }

    public static void main(String[] args) {
        new TestApp();
    }
}
