package me.piggypiglet.framework.http.annotations;

import com.google.inject.BindingAnnotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------

/**
 * Annotation for the default html filewrapper binding
 */
@BindingAnnotation
@Target({ElementType.FIELD}) @Retention(RetentionPolicy.RUNTIME)
public @interface DefaultHTTP {
}
