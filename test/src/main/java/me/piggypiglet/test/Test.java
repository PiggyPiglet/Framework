package me.piggypiglet.test;

import me.piggypiglet.framework.Framework;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class Test {
    public static void main(String[] args) {
        Framework.builder()
                .commandPrefix("")
                .pckg("me.piggypiglet.test")
                .file(true, "config", "/config.json", "./config.json", Config.class)
                .build()
                .init();
    }
}
