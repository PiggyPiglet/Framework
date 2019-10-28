package me.piggypiglet.framework.scanning;

import me.piggypiglet.framework.utils.annotations.reflection.Disabled;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class Scanner {
    protected abstract <T> Set<Class<? extends T>> provideSubTypesOf(Class<T> type);

    protected abstract Set<Class<?>> provideTypesAnnotatedWith(Class<? extends Annotation> annotation);

    protected abstract Set<Method> provideMethodsAnnotatedWith(Class<? extends Annotation> annotation);

    protected abstract Set<Constructor> provideConstructorsWithAnyParamAnnotated(Class<? extends Annotation> annotation);

    protected abstract Set<Field> provideFieldsAnnotatedWith(Class<? extends Annotation> annotation);

    /**
     * Gets all sub types in hierarchy of a given type
     * @param type Interface to search for sub classes under
     * @param <T> Interface type
     * @return Set of found classes
     */
    public <T> Set<Class<? extends T>> getSubTypesOf(Class<T> type) {
        return provideSubTypesOf(type).stream().filter(this::notDisabled).filter(c -> !Modifier.isAbstract(c.getModifiers())).collect(Collectors.toSet());
    }

    /**
     * Get types annotated with a given annotation
     * @param annotation Annotation to search for
     * @return Set of found classes
     */
    public Set<Class<?>> getTypesAnnotatedWith(Class<? extends Annotation> annotation) {
        return provideTypesAnnotatedWith(annotation).stream().filter(this::notDisabled).collect(Collectors.toSet());
    }

    /**
     * Get classes that contain a specific annotation
     * @param annotation Annotation to search for
     * @return Set of found classes
     */
    public Set<Class<?>> getClassesWithAnnotatedMethods(Class<? extends Annotation> annotation) {
        return provideMethodsAnnotatedWith(annotation).stream().map(Method::getDeclaringClass).filter(this::notDisabled).collect(Collectors.toSet());
    }

    /**
     * Get parameters in constructors that are annotated with a specific annotation
     * @param annotation Annotation to search for
     * @return Set of parameters
     */
    public Set<Parameter> getParametersInConstructorsAnnotatedWith(Class<? extends Annotation> annotation) {
        return provideConstructorsWithAnyParamAnnotated(annotation).stream()
                .filter(p -> notDisabled(p.getDeclaringClass()))
                .flatMap(c -> Arrays.stream(c.getParameters())
                        .filter(p -> Arrays.stream(p.getAnnotations())
                                .map(Annotation::annotationType)
                                .anyMatch(pt -> pt == annotation)))
                .collect(Collectors.toSet());
    }

    /**
     * Get fields that are annotated with a specific annotation
     * @param annotation Annotation to search for
     * @return Set of found fields
     */
    public Set<Field> getFieldsAnnotatedWith(Class<? extends Annotation> annotation) {
        return provideFieldsAnnotatedWith(annotation).stream().filter(c -> notDisabled(c.getDeclaringClass())).collect(Collectors.toSet());
    }

    private boolean notDisabled(Class clazz) {
        return !clazz.isAnnotationPresent(Disabled.class);
    }
}