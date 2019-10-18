/*
 * MIT License
 *
 * Copyright (c) 2019 PiggyPiglet
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

package me.piggypiglet.framework.scanning.implementations;

import me.piggypiglet.framework.Framework;
import me.piggypiglet.framework.utils.annotations.reflection.Disabled;
import me.piggypiglet.framework.scanning.Scanner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Constructor;
import java.util.HashSet;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * org.reflections wrapper for classpath scanning
 */
public final class Reflections extends Scanner {
    private final Set<org.reflections.Reflections> reflections;

    public Reflections(String pckg, org.reflections.scanners.Scanner... scanners) {
        this.reflections = new HashSet<>(Arrays.asList(
                new org.reflections.Reflections(pckg, scanners),
                new org.reflections.Reflections(Framework.class.getPackage(), scanners)
        ));
    }

    @Override
    protected <T> Set<Class<? extends T>> provideSubTypesOf(Class<T> type) {
        return reflections.stream().flatMap(r -> r.getSubTypesOf(type).stream()).collect(Collectors.toSet());
    }

    @Override
    protected Set<Class<?>> provideTypesAnnotatedWith(Class<? extends Annotation> annotation) {
        return reflections.stream().flatMap(r -> r.getTypesAnnotatedWith(annotation).stream()).collect(Collectors.toSet());
    }

    @Override
    protected Set<Method> provideMethodsAnnotatedWith(Class<? extends Annotation> annotation) {
        return reflections.stream().flatMap(r -> r.getMethodsAnnotatedWith(annotation).stream()).collect(Collectors.toSet());
    }

    @Override
    protected Set<Constructor> provideConstructorsWithAnyParamAnnotated(Class<? extends Annotation> annotation) {
        return reflections.stream().flatMap(r -> r.getConstructorsWithAnyParamAnnotated(annotation).stream()).collect(Collectors.toSet());
    }

    @Override
    protected Set<Field> provideFieldsAnnotatedWith(Class<? extends Annotation> annotation) {
        return reflections.stream().flatMap(r -> r.getFieldsAnnotatedWith(annotation).stream()).collect(Collectors.toSet());
    }
}
