package me.piggypiglet.framework.minecraft.versions.material.framework;

import me.piggypiglet.framework.minecraft.versions.Versions;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE) @Retention(RetentionPolicy.RUNTIME)
public @interface MaterialVersion {
    Versions value();
}
