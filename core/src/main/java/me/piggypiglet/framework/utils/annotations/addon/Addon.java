package me.piggypiglet.framework.utils.annotations.addon;

import me.piggypiglet.framework.registerables.ShutdownRegisterable;
import me.piggypiglet.framework.utils.annotations.registerable.Startup;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------

/**
 * Configuration annotation for addons
 */
@Target(ElementType.TYPE) @Retention(RetentionPolicy.RUNTIME)
public @interface Addon {
    /**
     * Array of startup registerables to be registered
     * @return Array of Startup annotations
     */
    Startup[] startup() default {};

    /**
     * Array of shutdown registerables to be registered
     * @return Array of ShutdownRegisterable classes
     */
    Class<? extends ShutdownRegisterable>[] shutdown() default {};

    /**
     * Array of files to be registered
     * @return Array of File annotation
     */
    File[] files() default {};
}
