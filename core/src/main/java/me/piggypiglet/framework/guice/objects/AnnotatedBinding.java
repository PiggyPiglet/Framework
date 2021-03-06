/*
 * MIT License
 *
 * Copyright (c) 2019-2020 PiggyPiglet
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package me.piggypiglet.framework.guice.objects;

import com.google.inject.TypeLiteral;

import java.lang.annotation.Annotation;

public final class AnnotatedBinding<T> {
    private final Object type;
    private final Object annotation;
    private final T instance;
    private final boolean typeLiteral;
    private final boolean annotationInstance;

    public AnnotatedBinding(TypeLiteral<? super T> literal, Annotation annotation, T instance) {
        this((Object) literal, annotation, instance);
    }

    public AnnotatedBinding(Class<? super T> clazz, Annotation annotation, T instance) {
        this((Object) clazz, annotation, instance);
    }

    public AnnotatedBinding(TypeLiteral<? super T> literal, Class<? extends Annotation> annotation, T instance) {
        this((Object) literal, annotation, instance);
    }

    public AnnotatedBinding(Class<? super T> clazz, Class<? extends Annotation> annotation, T instance) {
        this((Object) clazz, annotation, instance);
    }

    private AnnotatedBinding(Object type, Object annotation, T instance) {
        this.type = type;
        this.annotation = annotation;
        this.instance = instance;
        typeLiteral = type instanceof TypeLiteral;
        annotationInstance = annotation instanceof Annotation;
    }

    @SuppressWarnings("unchecked")
    public TypeLiteral<? super T> getTypeLiteral() {
        return (TypeLiteral<? super T>) type;
    }

    @SuppressWarnings("unchecked")
    public Class<? super T> getTypeClazz() {
        return (Class<? super T>) type;
    }

    public Annotation getAnnotationInstance() {
        return (Annotation) annotation;
    }

    @SuppressWarnings("unchecked")
    public Class<? extends Annotation> getAnnotationClass() {
        return (Class<? extends Annotation>) annotation;
    }

    public T getInstance() {
        return instance;
    }

    public boolean isTypeLiteral() {
        return typeLiteral;
    }

    public boolean isAnnotationInstance() {
        return annotationInstance;
    }
}