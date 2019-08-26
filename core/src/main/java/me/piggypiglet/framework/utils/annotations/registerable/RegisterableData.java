package me.piggypiglet.framework.utils.annotations.registerable;

import me.piggypiglet.framework.bootstrap.BootPriority;
import me.piggypiglet.framework.registerables.StartupRegisterable;

import java.lang.annotation.Annotation;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class RegisterableData {
    private final Class<? extends StartupRegisterable> registerable;
    private final BootPriority priority;

    public RegisterableData(Annotation startup) {
        if (startup instanceof Startup) {
            new RegisterableData((Startup) startup);
        } else if (startup instanceof AddonStartup) {
            new RegisterableData((AddonStartup) startup);
        }

        throw new RuntimeException("Invalid annotation provided to registerable data.");
    }

    private RegisterableData(Startup startup) {
        this.registerable = startup.value();
        this.priority = startup.priority();
    }

    private RegisterableData(AddonStartup startup) {
        this.registerable = startup.value();
        this.priority = startup.priority();
    }

    public Class<? extends StartupRegisterable> getRegisterable() {
        return registerable;
    }

    public BootPriority getPriority() {
        return priority;
    }
}
