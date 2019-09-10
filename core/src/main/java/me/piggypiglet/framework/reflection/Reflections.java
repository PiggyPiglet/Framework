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

package me.piggypiglet.framework.reflection;

import me.piggypiglet.framework.utils.annotations.reflection.Disabled;
import org.reflections.scanners.Scanner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Reflections wrapper to filter out classes annotated with @Disabled
 */
public final class Reflections {
    private final Set<org.reflections.Reflections> reflections;

    public Reflections(String pckg, Scanner... scanners) {
        this.reflections = new HashSet<>(Arrays.asList(
                new org.reflections.Reflections(pckg, scanners),
                new org.reflections.Reflections("me.piggypiglet.framework", scanners)
        ));
    }

    /**
     * Gets all sub types in hierarchy of a given type
     * @param type Interface to search for sub classes under
     * @param <T> Interface type
     * @return Set of found classes
     */
    public <T> Set<Class<? extends T>> getSubTypesOf(Class<T> type) {
        return reflections.stream().flatMap(r -> r.getSubTypesOf(type).stream()).filter(this::isDisabled).collect(Collectors.toSet());
    }

    /**
     * Get types annotated with a given annotation
     * @param annotation Annotation to search for
     * @return Set of found classes
     */
    public Set<Class<?>> getTypesAnnotatedWith(Class<? extends Annotation> annotation) {
        return reflections.stream().flatMap(r -> r.getTypesAnnotatedWith(annotation).stream()).filter(this::isDisabled).collect(Collectors.toSet());
    }

    /**
     * Get classes that contain a specific annotation
     * @param annotation Annotation to search for
     * @return Set of found classes
     */
    public Set<Class<?>> getClassesWithAnnotatedMethods(Class<? extends Annotation> annotation) {
        return reflections.stream().flatMap(r -> r.getMethodsAnnotatedWith(annotation).stream().map(Method::getDeclaringClass)).filter(this::isDisabled).collect(Collectors.toSet());
    }

    /**
     * Get constructors that are annotated with a specific annotation
     * @param annotation Annotation to search for
     * @return Set of constructors
     */
    public Set<Constructor> getParametersAnnotatedWith(Class<? extends Annotation> annotation) {
        return reflections.stream().flatMap(r -> r.getConstructorsWithAnyParamAnnotated(annotation).stream()).filter(p -> isDisabled(p.getDeclaringClass())).collect(Collectors.toSet());
    }

    /**
     * Get fields that are annotated with a specific annotation
     * @param annotation Annotation to search for
     * @return Set of found fields
     */
    public Set<Field> getFieldsAnnotatedWith(Class<? extends Annotation> annotation) {
        return reflections.stream().flatMap(r -> r.getFieldsAnnotatedWith(annotation).stream()).filter(c -> isDisabled(c.getDeclaringClass())).collect(Collectors.toSet());
    }

    private boolean isDisabled(Class clazz) {
        return Arrays.stream(clazz.getAnnotations()).noneMatch(a -> a instanceof Disabled);
    }
}
