package me.piggypiglet.framework.utils.annotations.files;

import com.google.inject.BindingAnnotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
@BindingAnnotation
@Target({ElementType.FIELD}) @Retention(RetentionPolicy.RUNTIME)
public @interface Users {
}