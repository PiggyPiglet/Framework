package me.piggypiglet.framework.utils.annotations.registerable;

import me.piggypiglet.framework.bootstrap.BootPriority;
import me.piggypiglet.framework.registerables.StartupRegisterable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
@Target(ElementType.TYPE) @Retention(RetentionPolicy.RUNTIME)
public @interface Startup {
    Class<? extends StartupRegisterable> value();

    BootPriority priority() default BootPriority.MANUAL;
}
