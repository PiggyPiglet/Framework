package me.piggypiglet.framework.guice.objects;

import java.lang.annotation.Annotation;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class AnnotatedBinding {
    private final Class clazz;
    private final Annotation annotation;
    private final Object instance;

    public AnnotatedBinding(Class clazz, Annotation annotation, Object instance) {
        this.clazz = clazz;
        this.annotation = annotation;
        this.instance = instance;
    }

    public Class getClazz() {
        return clazz;
    }

    public Annotation getAnnotation() {
        return annotation;
    }

    public Object getInstance() {
        return instance;
    }
}