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

package me.piggypiglet.framework.scanning.implementations;

import com.google.common.collect.ImmutableSet;
import me.piggypiglet.framework.Framework;
import me.piggypiglet.framework.scanning.Scanner;
import me.piggypiglet.framework.utils.StringUtils;
import me.piggypiglet.framework.utils.annotations.reflection.Disabled;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.CodeSource;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public final class ZISScanner extends Scanner {
    private final Set<Class<?>> classes = new HashSet<>();
    private final Set<Constructor<?>> constructors;
    private final Set<Method> methods;
    private final Set<Field> fields;

    public ZISScanner(Class<?> main, String pckg, String[] exclusions) {
        final String framework = Framework.class.getPackage().getName().replace('.', '/');
        pckg = pckg.replace('.', '/');
        exclusions = Arrays.stream(exclusions).map(s -> s.replace('.', '/')).toArray(String[]::new);

        try {
            final ClassLoader loader = main.getClassLoader();
            final CodeSource src = main.getProtectionDomain().getCodeSource();

            if (src != null) {
                final ZipInputStream zip = new ZipInputStream(src.getLocation().openStream());
                ZipEntry entry;

                while((entry = zip.getNextEntry()) != null) {
                    final String name = entry.getName();

                    if (name.endsWith(".class") && (name.startsWith(pckg) || name.startsWith(framework)) && !StringUtils.startsWithAny(name, exclusions)) {
                        final Class<?> clazz;

                        try {
                            clazz = loader.loadClass(name.replace('/', '.').replace(".class", ""));
                        } catch (Exception e) {
                            continue;
                        }

                        if (!clazz.isAnnotationPresent(Disabled.class)) {
                            classes.add(clazz);
                        }
                    }
                }
            }

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
