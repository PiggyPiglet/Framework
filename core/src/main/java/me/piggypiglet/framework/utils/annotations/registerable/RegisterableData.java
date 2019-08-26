package me.piggypiglet.framework.utils.annotations.registerable;

import me.piggypiglet.framework.bootstrap.BootPriority;
import me.piggypiglet.framework.registerables.StartupRegisterable;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class RegisterableData {
    private final Class<? extends StartupRegisterable> registerable;
    private final BootPriority priority;

    public RegisterableData(Class<? extends StartupRegisterable> registerable) {
        this(registerable, BootPriority.MANUAL);
    }

    public RegisterableData(Class<? extends StartupRegisterable> registerable, BootPriority priority) {
        this.registerable = registerable;
        this.priority = priority;
    }

    public RegisterableData(Startup startup) {
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
