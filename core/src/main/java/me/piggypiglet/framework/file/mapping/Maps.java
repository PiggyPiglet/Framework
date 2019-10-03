package me.piggypiglet.framework.file.mapping;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE) @Retention(RetentionPolicy.RUNTIME)
public @interface Maps {
    /**
     * Map an object to a config.
     * @return String reference of the config
     */
    String value();
}
