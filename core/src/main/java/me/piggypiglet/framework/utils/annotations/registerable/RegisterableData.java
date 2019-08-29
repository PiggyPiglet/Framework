package me.piggypiglet.framework.utils.annotations.registerable;

import me.piggypiglet.framework.bootstrap.BootPriority;
import me.piggypiglet.framework.registerables.StartupRegisterable;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------

/**
 * StartupRegisterable wrapper for applications
 */
public final class RegisterableData {
    private final Class<? extends StartupRegisterable> registerable;
    private final BootPriority priority;

    /**
     * Populate the object with your registerable, and BootPriority.MANUAL
     * @param registerable StartupRegisterable
     */
    public RegisterableData(Class<? extends StartupRegisterable> registerable) {
        this(registerable, BootPriority.MANUAL);
    }

    /**
     * Populate the object with your registerable, and a custom boot priority
     * @param registerable StartupRegisterable
     * @param priority BootPriority
     */
    public RegisterableData(Class<? extends StartupRegisterable> registerable, BootPriority priority) {
        this.registerable = registerable;
        this.priority = priority;
    }

    /**
     * Retrieve registerable info from the Startup annotation (addons)
     * @param startup Startup annotation
     */
    public RegisterableData(Startup startup) {
        this.registerable = startup.value();
        this.priority = startup.priority();
    }

    /**
     * @return StartupRegisterable
     */
    public Class<? extends StartupRegisterable> getRegisterable() {
        return registerable;
    }

    /**
     * @return BootPriority
     */
    public BootPriority getPriority() {
        return priority;
    }
}
