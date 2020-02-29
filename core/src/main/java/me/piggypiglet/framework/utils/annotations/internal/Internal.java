package me.piggypiglet.framework.utils.annotations.internal;

import com.google.inject.BindingAnnotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@BindingAnnotation
@Target({ElementType.PARAMETER, ElementType.FIELD})
public @interface Internal {
    String value();
}
