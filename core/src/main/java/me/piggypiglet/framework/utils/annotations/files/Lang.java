package me.piggypiglet.framework.utils.annotations.files;

import com.google.inject.BindingAnnotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * For internal use only, do not attempt to use this for a binding.
 */
@BindingAnnotation
@Target({ElementType.FIELD, ElementType.PARAMETER}) @Retention(RetentionPolicy.RUNTIME)
public @interface Lang {
}
