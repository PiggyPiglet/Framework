package me.piggypiglet.framework.reflection;

import me.piggypiglet.framework.utils.annotations.reflection.Disabled;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class Reflections {
    private final org.reflections.Reflections reflections;

    public Reflections(org.reflections.Reflections reflections) {
        this.reflections = reflections;
    }

    public <T> Set<Class<? extends T>> getSubTypesOf(Class<T> type) {
        return reflections.getSubTypesOf(type).stream().filter(this::isDisabled).collect(Collectors.toSet());
    }

    public Set<Class<?>> getTypesAnnotatedWith(Class<? extends Annotation> annotation) {
        return reflections.getTypesAnnotatedWith(annotation);
    }

    public Set<Class<?>> getClassesWithAnnotatedMethods(Class<? extends Annotation> annotation) {
        return reflections.getMethodsAnnotatedWith(annotation).stream().map(Method::getDeclaringClass).filter(this::isDisabled).collect(Collectors.toSet());
    }

    private boolean isDisabled(Class clazz) {
        return Arrays.stream(clazz.getAnnotations()).noneMatch(a -> a instanceof Disabled);
    }
}
