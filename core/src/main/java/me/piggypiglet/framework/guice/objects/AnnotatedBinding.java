package me.piggypiglet.framework.guice.objects;

import java.lang.annotation.Annotation;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class AnnotatedBinding {
    private final Class clazz;
    private final Object annotation;
    private final Object instance;
    private boolean object;

    public AnnotatedBinding(Class clazz, Annotation annotation, Object instance) {
        this.object = true;
        this.clazz = clazz;
        this.annotation = annotation;
        this.instance = instance;
    }

    public AnnotatedBinding(Class clazz, Class<? extends Annotation> annotation, Object instance) {
        this.object = false;
        this.clazz = clazz;
        this.annotation = annotation;
        this.instance = instance;
    }

    public Class getClazz() {
        return clazz;
    }

    @SuppressWarnings("unchecked")
    public Class<? extends Annotation> getClassAnnotation() {
        return (Class<? extends Annotation>) annotation;
    }

    public Annotation getObjectAnnotation() {
        return (Annotation) annotation;
    }

    public Object getInstance() {
        return instance;
    }

    public boolean isObject() {
        return object;
    }
}