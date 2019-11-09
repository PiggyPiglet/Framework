package me.piggypiglet.framework.utils.annotations.addon;

import me.piggypiglet.framework.lang.LangEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE) @Retention(RetentionPolicy.RUNTIME)
public @interface Langs {
    String file();

    Class<? extends Enum<? extends LangEnum>> clazz();

    String[] values();
}
