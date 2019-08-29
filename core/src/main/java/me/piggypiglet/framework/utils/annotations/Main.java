package me.piggypiglet.framework.utils.annotations;

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
 * Default annotation for the application's main class, when set using FrameworkBuilder#main(Object)
 */
@BindingAnnotation
@Target(ElementType.FIELD) @Retention(RetentionPolicy.RUNTIME)
public @interface Main {
}
