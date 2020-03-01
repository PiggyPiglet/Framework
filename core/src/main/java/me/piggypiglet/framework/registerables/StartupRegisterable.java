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

package me.piggypiglet.framework.registerables;

import com.google.inject.TypeLiteral;
import me.piggypiglet.framework.guice.objects.AnnotatedBinding;
import me.piggypiglet.framework.guice.objects.Binding;
import me.piggypiglet.framework.guice.objects.Injector;
import me.piggypiglet.framework.utils.annotations.wrapper.AnnotationWrapper;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class StartupRegisterable {
    protected Injector injector;

    private final List<Binding<?>> bindings = new ArrayList<>();
    private final List<AnnotatedBinding<?>> annotatedBindings = new ArrayList<>();
    private final List<Class<?>> staticInjections = new ArrayList<>();

    /**
     * Code to be ran on the registerable's execution.
     */
    protected abstract void execute();

    /**
     * Add a binding to guice with a specific class and instance.
     *
     * @param interfaze Class the object will be referenced by
     * @param instance  Object that will be binded to the class
     * @param <T>       Object type
     */
    protected <T> void addBinding(Class<? super T> interfaze, T instance) {
        bindings.add(new Binding<>(interfaze, instance));
    }

    /**
     * Add a binding to guice with a specific class &amp; generic, and instance.
     *
     * @param type      Class the object will be referenced by
     * @param instance  Object that will be binded
     * @param <T>       Type parameter belonging to interfaze
     */
    protected <T> void addBinding(TypeLiteral<? super T> type, T instance) {
        bindings.add(new Binding<>(type, instance));
    }

    /**
     * Add a binding to guice, reference class is deduced from the instance itself.
     *
     * @param instance Object of the instance to be binded
     */
    @SuppressWarnings("unchecked")
    protected <T> void addBinding(T instance) {
        addBinding((Class<T>) instance.getClass(), instance);
    }

    /**
     * Add a binding to guice that's only injectable when a specific annotation is provided.
     *
     * @param type       Reference type to be binded to
     * @param annotation Annotation instance
     * @param instance   Instance of the object to be binded
     * @param <T>        Object type
     */
    protected <T> void addAnnotatedBinding(@NotNull final TypeLiteral<? super T> type, @NotNull final Class<? extends Annotation> annotation,
                                           @NotNull final T instance) {
        addAnnotatedBinding(type, new AnnotationWrapper(annotation), instance);
    }

    /**
     * Add a binding to guice that's only injectable when a specific annotation is provided.
     *
     * @param type       Reference type to be binded to
     * @param annotation Annotation instance
     * @param instance   Instance of the object to be binded
     * @param <T>        Object type
     */
    protected <T> void addAnnotatedBinding(@NotNull final TypeLiteral<? super T> type, @NotNull final Annotation annotation,
                                           @NotNull final T instance) {
        addAnnotatedBinding(type, new AnnotationWrapper(annotation), instance);
    }

    /**
     * Add a binding to guice that's only injectable when a specific annotation is provided.
     *
     * @param type       Reference type to be binded to
     * @param annotation Annotation instance
     * @param instance   Instance of the object to be binded
     * @param <T>        Object type
     */
    protected <T> void addAnnotatedBinding(TypeLiteral<? super T> type, AnnotationWrapper annotation, T instance) {
        annotatedBindings.add(
                new AnnotatedBinding<>(type, annotation, instance)
        );
    }

    protected <T> void addAnnotatedBinding(@NotNull final Class<? super T> interfaze, @NotNull final Class<? extends Annotation> annotation,
                                           @NotNull final T instance) {
        addAnnotatedBinding(interfaze, new AnnotationWrapper(annotation), instance);
    }

    protected <T> void addAnnotatedBinding(@NotNull final Class<? super T> interfaze, @NotNull final Annotation annotation,
                                           @NotNull final T instance) {
        addAnnotatedBinding(interfaze, new AnnotationWrapper(annotation), instance);
    }

    /**
     * Add a binding to guice that's only injectable when a specific annotation is provided.
     *
     * @param interfaze  Reference class to be binded to
     * @param annotation Annotation instance
     * @param instance   Instance of the object to be binded
     * @param <T>        Object type
     */
    protected <T> void addAnnotatedBinding(Class<? super T> interfaze, AnnotationWrapper annotation, T instance) {
        annotatedBindings.add(
                new AnnotatedBinding<>(interfaze, annotation, instance)
        );
    }

    /**
     * Request classes to have their static injection honoured
     *
     * @param classes Varargs of classes
     */
    protected void requestStaticInjections(Class<?>... classes) {
        staticInjections.addAll(Arrays.asList(classes));
    }

    /**
     * Run the registerable
     *
     * @param injector Injector instance that will be provided to the registerable
     */
    public void run(Injector injector) {
        this.injector = injector;
        execute();
    }

    /**
     * Get a list of bindings set by the registerable
     *
     * @return List of bindings
     */
    public List<Binding<?>> getBindings() {
        return bindings;
    }

    /**
     * Get a list of annotated binding objects set by the registerable
     *
     * @return List of annotatedbinding data objects
     */
    public List<AnnotatedBinding<?>> getAnnotatedBindings() {
        return annotatedBindings;
    }

    /**
     * Get any static injections requested by the registerable
     *
     * @return List of classes
     */
    public List<Class<?>> getStaticInjections() {
        return staticInjections;
    }
}