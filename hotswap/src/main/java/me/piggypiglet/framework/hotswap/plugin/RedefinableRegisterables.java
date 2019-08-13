package me.piggypiglet.framework.hotswap.plugin;

import me.piggypiglet.framework.registerables.StartupRegisterable;
import me.piggypiglet.framework.registerables.startup.commands.CommandsRegisterable;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public enum RedefinableRegisterables {
    COMMANDS(CommandsRegisterable.class);

    private final Class<? extends StartupRegisterable> registerable;

    RedefinableRegisterables(Class<? extends StartupRegisterable> registerable) {
        this.registerable = registerable;
    }

    public static boolean redefinable(StartupRegisterable registerable) {
        for (RedefinableRegisterables r : values()) {
            if (r.registerable == registerable.getClass()) {
                return true;
            }
        }

        return false;
    }
}
