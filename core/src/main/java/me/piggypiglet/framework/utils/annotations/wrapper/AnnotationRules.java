package me.piggypiglet.framework.utils.annotations.wrapper;

import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public final class AnnotationRules {
    private static final BiPredicate<AnnotatedElement, AnnotationWrapper> REQUIRED = AnnotationUtils::isAnnotationPresent;

    private final List<Predicate<AnnotatedElement>> rules;

    private AnnotationRules(@NotNull final List<Predicate<AnnotatedElement>> rules) {
        this.rules = rules;
    }

    @NotNull
    public List<Predicate<AnnotatedElement>> getRules() {
        return rules;
    }

    @NotNull
    public static AnnotationRules hasAnnotation(@NotNull final Class<? extends Annotation> annotation) {
        return builder().hasAnnotation(annotation).build();
    }

    @NotNull
    public static AnnotationRules hasAnnotation(@NotNull final Annotation annotation) {
        return builder().hasAnnotation(annotation).build();
    }

    @NotNull
    public static AnnotationRules of(@NotNull final Predicate<AnnotatedElement> rule) {
        return builder().customRule(rule).build();
    }

    @NotNull
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final List<Predicate<AnnotatedElement>> rules = new ArrayList<>();

        private Builder() {}

        @NotNull
        public Builder hasAnnotation(@NotNull final Class<? extends Annotation> annotation) {
            return hasAnnotation(new AnnotationWrapper(annotation));
        }

        @NotNull
        public Builder hasAnnotation(@NotNull final Annotation annotation) {
            return hasAnnotation(new AnnotationWrapper(annotation));
        }

        @NotNull
        private Builder hasAnnotation(@NotNull final AnnotationWrapper annotation) {
            return customRule(element -> REQUIRED.test(element, annotation));
        }

        @NotNull
        public Builder customRule(@NotNull final Predicate<AnnotatedElement> rule) {
            rules.add(rule);
            return this;
        }

        @NotNull
        public AnnotationRules build() {
            return new AnnotationRules(rules);
        }
    }
}
