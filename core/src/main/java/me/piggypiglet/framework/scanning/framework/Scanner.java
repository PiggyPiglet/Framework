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

package me.piggypiglet.framework.scanning.framework;

import me.piggypiglet.framework.utils.annotations.wrapper.AnnotationWrapper;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.util.Set;

/**
 * Scanner interface that defines the explicit functionality
 * RPF uses. Check the implementation you're using for logic/
 * comparing definitions.
 */
public interface Scanner {
    /**
     * Get all known sub types of a specific type.
     *
     * @param type Master type
     * @param <T>  Type generic
     * @return Set of classes
     */
    <T> Set<Class<? extends T>> getSubTypesOf(@NotNull Class<T> type);

    /**
     * Get all known classes that are annotated with a specific annotation type.
     *
     * @param annotations Annotation
     * @return Set of classes
     */
    Set<Class<?>> getClassesAnnotatedWith(@NotNull final AnnotationWrapper... annotations);

    /**
     * Get all known classes that have methods annotated with a specific annotation
     * type.
     *
     * @param annotations Annotation
     * @return Set of classes
     */
    Set<Class<?>> getClassesWithAnnotatedMethods(@NotNull final AnnotationWrapper... annotations);

    /**
     * Get all known constructor parameters that are annotated with a specific
     * annotation type.
     *
     * @param annotations Annotation class
     * @return Set of parameters
     */
    Set<Parameter> getParametersAnnotatedWithInConstructors(@NotNull final AnnotationWrapper... annotations);

    /**
     * Get all known fields that are annotated with a specific annotation type.
     *
     * @param annotations Annotation class
     * @return Set of fields
     */
    Set<Field> getFieldsAnnotatedWith(@NotNull final AnnotationWrapper... annotations);
}