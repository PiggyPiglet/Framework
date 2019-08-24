package me.piggypiglet.test;

import me.piggypiglet.framework.Framework;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class Test {
    private Test() {
        Framework.builder()
                .main(this)
                .pckg("me.piggypiglet.test")
                .commandPrefix("!")
                .build()
                .init();
    }

    public static void main(String[] args) {
        new Test();
    }
}
