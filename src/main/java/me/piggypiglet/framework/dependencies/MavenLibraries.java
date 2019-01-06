package me.piggypiglet.framework.dependencies;

import javax.annotation.Nonnull;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// ------------------------------
// Copyright (c) PiggyPiglet 2018
// https://www.piggypiglet.me
// Credit to lucko, https://lucko.me luck@lucko.me
// ------------------------------
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MavenLibraries {
    @Nonnull
    MavenLibrary[] value() default {};
}
