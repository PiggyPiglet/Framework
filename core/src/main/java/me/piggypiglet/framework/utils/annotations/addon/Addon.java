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
@Target(ElementType.TYPE) @Retention(RetentionPolicy.RUNTIME)
public @interface Addon {
    Startup[] startup() default {};

    Class<? extends ShutdownRegisterable>[] shutdown() default {};

    File[] files() default {};
}
