package me.piggypiglet.test;

import me.piggypiglet.framework.Framework;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class Test {
    public static void main(String[] args) {
        Framework.builder()
                .commandPrefix("test")
                .pckg("me.piggypiglet.test")
                .build()
                .init();
    }
}
