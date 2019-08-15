package me.piggypiglet.test;

import me.piggypiglet.framework.Framework;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class Test {
    public static void main(String[] args) {
        Framework.builder()
                .main(Test.class)
                .pckg("me.piggypiglet.test")
                .commandPrefix("!")
                .build()
                .init();
    }
}
