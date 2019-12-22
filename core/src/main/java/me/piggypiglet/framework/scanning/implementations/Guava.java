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

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import me.piggypiglet.framework.Framework;
import me.piggypiglet.framework.scanning.Scanner;
import me.piggypiglet.framework.utils.StringUtils;
import me.piggypiglet.framework.utils.annotations.reflection.Disabled;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class Guava extends Scanner {
    private final Set<Class<?>> classes;
    private final Set<Constructor<?>> constructors;
    private final Set<Method> methods;
    private final Set<Field> fields;

    @SuppressWarnings("UnstableApiUsage")
    public Guava(Class<?> main, String pckg, String[] exclusions) {
        try {
            final ClassPath classPath = ClassPath.from(main.getClassLoader());
            final Predicate<ClassPath.ClassInfo> allowThrough = c -> !StringUtils.startsWithAny(c.getPackageName(), exclusions);
            final String frameworkPackage = Framework.class.getPackage().getName();
            Stream<Class<?>> classStream = classPath.getTopLevelClassesRecursive(pckg).parallelStream()
                    .filter(allowThrough)
                    .map(ClassPath.ClassInfo::load);

            if (!frameworkPackage.startsWith(pckg)) {
                final Stream<Class<?>> frameworkStream = ClassPath.from(Framework.class.getClassLoader()).getTopLevelClassesRecursive(frameworkPackage).parallelStream()
                        .filter(allowThrough)
                        .map(ClassPath.ClassInfo::load);

                classStream = Stream.concat(classStream, frameworkStream);
            }

            classes = classStream
                    .filter(c -> !c.isAnnotationPresent(Disabled.class))
                    .collect(Collectors.collectingAndThen(Collectors.toList(), ImmutableSet::copyOf));;
            constructors = get(Class::getDeclaredConstructors);
            methods = get(Class::getDeclaredMethods);
            fields = get(Class::getDeclaredFields);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    protected <T> Set<Class<? extends T>> provideSubTypesOf(Class<T> type) {
        return classes.parallelStream()
                .filter(type::isAssignableFrom)
                .map(c -> (Class<? extends T>) c)
                .collect(Collectors.toSet());
    }

    @Override
    protected Set<Class<?>> provideTypesAnnotatedWith(Class<? extends Annotation> annotation) {
        return classes.parallelStream()
                .filter(c -> c.isAnnotationPresent(annotation))
                .collect(Collectors.toSet());
    }

    @Override
    protected Set<Method> provideMethodsAnnotatedWith(Class<? extends Annotation> annotation) {
        return methods.parallelStream()
                .filter(m -> m.isAnnotationPresent(annotation))
                .collect(Collectors.toSet());
    }

    @Override
    protected Set<Constructor<?>> provideConstructorsWithAnyParamAnnotated(Class<? extends Annotation> annotation) {
        return constructors.parallelStream()
                .filter(c -> Arrays.stream(c.getParameters()).anyMatch(p -> p.isAnnotationPresent(annotation)))
                .collect(Collectors.toSet());
    }

    @Override
    protected Set<Field> provideFieldsAnnotatedWith(Class<? extends Annotation> annotation) {
        return fields.parallelStream()
                .filter(f -> f.isAnnotationPresent(annotation))
                .collect(Collectors.toSet());
    }

    private <T extends AccessibleObject> Set<T> get(Function<Class<?>, T[]> map) {
        return classes.parallelStream()
                .map(map)
                .flatMap(Arrays::stream)
                .collect(Collectors.collectingAndThen(Collectors.toList(), ImmutableSet::copyOf));
    }
}
