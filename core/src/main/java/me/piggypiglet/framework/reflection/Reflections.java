package me.piggypiglet.framework.reflection;

import me.piggypiglet.framework.utils.annotations.reflection.Disabled;

import java.lang.annotation.Annotation;
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
        return reflections.getSubTypesOf(type).stream().filter(c -> Arrays.stream(c.getAnnotations()).noneMatch(a -> a instanceof Disabled)).collect(Collectors.toSet());
    }

    public Set<Class<?>> getTypesAnnotatedWith(Class<? extends Annotation> annotation) {
        return reflections.getTypesAnnotatedWith(annotation);
    }
}
