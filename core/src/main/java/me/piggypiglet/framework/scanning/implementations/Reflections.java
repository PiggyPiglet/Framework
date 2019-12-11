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
import me.piggypiglet.framework.scanning.Scanner;
import me.piggypiglet.framework.utils.StringUtils;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * org.reflections wrapper for classpath scanning
 */
public final class Reflections extends Scanner {
    private final Set<org.reflections.Reflections> reflections;

    public Reflections(String pckg, String[] exclusions, org.reflections.scanners.Scanner... scanners) {
        this.reflections = new HashSet<>(Collections.singletonList(
            reflections(pckg, exclusions, scanners)
        ));

        final String frameworkPackage = Framework.class.getPackage().getName();

        if (!frameworkPackage.startsWith(pckg)) {
            reflections.add(reflections(frameworkPackage, exclusions, scanners));
        }
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

    private org.reflections.Reflections reflections(String pckg, String[] exclusions, org.reflections.scanners.Scanner... scanners) {
        return new org.reflections.Reflections(new ConfigurationBuilder()
                .useParallelExecutor()
                .setUrls(ClasspathHelper.forPackage(pckg))
                .setScanners(scanners)
                .filterInputsBy(s -> !StringUtils.anyStartWith(s, exclusions))
        );
    }
}
