package me.piggypiglet.framework.guice.objects;

import java.lang.annotation.Annotation;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class MainBinding {
    private final Class<?> clazz;
    private final Object instance;
    private Class<? extends Annotation> annotation = null;

    public MainBinding(Class<?> clazz, Object instance) {
        this.clazz = clazz;
        this.instance = instance;
    }

    public MainBinding(Class<?> clazz, Object instance, Class<? extends Annotation> annotation) {
        this.clazz = clazz;
        this.instance = instance;
        this.annotation = annotation;
    }

    public Class getClazz() {
        return clazz;
    }

    public Object getInstance() {
        return instance;
    }

    public Class<? extends Annotation> getAnnotation() {
        return annotation;
    }
}
