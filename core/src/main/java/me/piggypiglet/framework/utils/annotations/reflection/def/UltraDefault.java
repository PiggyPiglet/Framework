package me.piggypiglet.framework.utils.annotations.reflection.def;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * For internal use only
 */
@Target(ElementType.TYPE) @Retention(RetentionPolicy.RUNTIME)
public @interface UltraDefault {
}
