package me.piggypiglet.framework.utils.annotations.reflection;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------

/**
 * Add to a class in order for it not to be picked up by any auto registers, for example commands.
 */
@Target(ElementType.TYPE) @Retention(RetentionPolicy.RUNTIME)
public @interface Disabled {
}
