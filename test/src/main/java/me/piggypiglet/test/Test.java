package me.piggypiglet.test;

import me.piggypiglet.framework.Framework;
import me.piggypiglet.framework.utils.annotations.files.Config;

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
                .startup(TestRegisterable.class)
                .build()
                .init();
    }
}
