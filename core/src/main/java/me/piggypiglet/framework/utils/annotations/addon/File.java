package me.piggypiglet.framework.utils.annotations.addon;

import java.lang.annotation.*;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------

/**
 * Addon to contain info about a file an addon needs to register
 */
@Target(ElementType.TYPE) @Retention(RetentionPolicy.RUNTIME)
public @interface File {
    /**
     * @return File is a config or not
     */
    boolean config();

    /**
     * @return Name of the file
     */
    String name();

    /**
     * @return External path of the file
     */
    String externalPath();

    /**
     * @return Internal path of the file
     */
    String internalPath();

    /**
     * @return Annotation to bind the file to
     */
    Class<? extends Annotation> annotation();
}
