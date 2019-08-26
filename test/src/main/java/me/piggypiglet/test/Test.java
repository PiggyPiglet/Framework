package me.piggypiglet.test;

import me.piggypiglet.framework.Framework;
import me.piggypiglet.framework.utils.annotations.registerable.RegisterableData;

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
                .startup(new RegisterableData(HelloRegisterable.class))
                .build()
                .init();
    }

    public static void main(String[] args) {
        new Test();
    }
}
