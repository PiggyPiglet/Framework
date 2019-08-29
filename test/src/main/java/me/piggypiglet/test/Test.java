package me.piggypiglet.test;

import me.piggypiglet.framework.Framework;
import me.piggypiglet.framework.bootstrap.BootPriority;
import me.piggypiglet.framework.utils.annotations.Main;
import me.piggypiglet.framework.utils.annotations.registerable.RegisterableData;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class Test {
    private Test() {
        Framework.builder()
                .main(Object.class, Main.class, this)
                .pckg("me.piggypiglet.test")
                .commandPrefix("!")
                .startup(new RegisterableData(LoaderRegisterable.class, BootPriority.BEFORE_IMPL))
                .build()
                .init();
    }

    public static void main(String[] args) {
        new Test();
    }
}
