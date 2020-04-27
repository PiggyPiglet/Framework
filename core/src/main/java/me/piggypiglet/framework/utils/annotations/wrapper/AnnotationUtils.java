package me.piggypiglet.framework.utils.annotations.wrapper;

import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

public final class AnnotationUtils {
    private AnnotationUtils() {
        throw new AssertionError("This class cannot be initialized.");
    }

    public static boolean isAnnotationPresent(@NotNull final AnnotatedElement element, @NotNull final AnnotationWrapper annotation) {
        final Class<? extends Annotation> clazz = annotation.getAnnotationClass();
        final Annotation instance = annotation.getAnnotationInstance();

        if (clazz != null) return element.isAnnotationPresent(clazz);
        if (instance == null) throw new AssertionError("Both annotation class & instance are null in an AnnotationWrapper.");

        return element.getAnnotation(instance.annotationType()).equals(instance);
    }

    public static boolean rulesMatch(@NotNull final AnnotatedElement element, @NotNull final AnnotationRules rules) {
        return rules.getRules().stream()
                .allMatch(rule -> rule.test(element));
    }
}
