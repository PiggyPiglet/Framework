package me.piggypiglet.framework.registerables;

import com.google.inject.Injector;
import me.piggypiglet.framework.guice.objects.AnnotatedBinding;

import java.lang.annotation.Annotation;
import java.util.*;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public abstract class StartupRegisterable {
    protected Injector injector;

    private final Map<Class, Object> bindings = new HashMap<>();
    private final List<AnnotatedBinding> annotatedBindings = new ArrayList<>();
    private final List<Class> staticInjections = new ArrayList<>();

    /**
     * Code to be ran on the registerable's execution.
     */
    protected abstract void execute();

    /**
     * Add a binding to guice with a specific class and instance.
     * @param interfaze Class the object will be referenced by
     * @param instance Object that will be binded to the class
     */
    protected void addBinding(Class interfaze, Object instance) {
        bindings.put(interfaze, instance);
    }

    /**
     * Add a binding to guice, reference class is deduced from the instance itself.
     * @param instance Object of the instance to be binded
     */
    protected void addBinding(Object instance) {
        bindings.put(instance.getClass(), instance);
    }

    /**
     * Add a binding to guice that's only injectable when a specific annotation is provided.
     * @param interfaze Reference class to be binded to
     * @param annotation Class extending annotation
     * @param instance Instance of the object to be binded.
     */
    protected void addAnnotatedBinding(Class interfaze, Class<? extends Annotation> annotation, Object instance) {
        annotatedBindings.add(
                new AnnotatedBinding(interfaze, annotation, instance)
        );
    }

    /**
     * Add a binding to guice that's only injectable when a specific annotation is provided.
     * @param interfaze Reference class to be binded to
     * @param annotation Annotation instance
     * @param instance Instance of the object to be binded
     */
    protected void addAnnotatedBinding(Class interfaze, Annotation annotation, Object instance) {
        annotatedBindings.add(
                new AnnotatedBinding(interfaze, annotation, instance)
        );
    }

    /**
     * Request classes to have their static injection honoured
     * @param classes Varargs of classes
     */
    protected void requestStaticInjections(Class... classes) {
        staticInjections.addAll(Arrays.asList(classes));
    }

    /**
     * Run the registerable
     * @param injector Injector instance that will be provided to the registerable
     */
    public void run(Injector injector) {
        this.injector = injector;
        execute();
    }

    /**
     * Get a map of bindings to be made
     * @return Map with Class as key, Object as value
     */
    public Map<Class, Object> getBindings() {
        return bindings;
    }

    /**
     * Get a list of annotated binding objects set by the registerable
     * @return List of annotatedbinding data objects
     */
    public List<AnnotatedBinding> getAnnotatedBindings() {
        return annotatedBindings;
    }

    /**
     * Get any static injections requested by the registerable
     * @return List of classes
     */
    public List<Class> getStaticInjections() {
        return staticInjections;
    }
}