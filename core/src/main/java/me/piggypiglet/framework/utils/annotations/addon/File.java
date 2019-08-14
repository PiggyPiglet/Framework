package me.piggypiglet.framework.utils.annotations.addon;

import java.lang.annotation.*;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
@Target(ElementType.TYPE) @Retention(RetentionPolicy.RUNTIME)
public @interface File {
    boolean config();

    String name();

    String externalPath();

    String internalPath();

    Class<? extends Annotation> annotation();
}
