package me.piggypiglet.framework.utils.annotations.wrapper;

import com.google.auto.value.AutoValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.annotation.Annotation;

@AutoValue
public final class AnnotationWrapper {
    private final Class<? extends Annotation> annotationClass;
    private final Annotation annotationInstance;

    public AnnotationWrapper(@NotNull final Class<? extends Annotation> annotation) {
        this(annotation, null);
    }

    public AnnotationWrapper(@NotNull final Annotation annotation) {
        this(null, annotation);
    }

    private AnnotationWrapper(@Nullable final Class<? extends Annotation> annotationClass, @Nullable final Annotation annotationInstance) {
        this.annotationClass = annotationClass;
        this.annotationInstance = annotationInstance;
    }

    @Nullable
    public Class<? extends Annotation> getAnnotationClass() {
        return annotationClass;
    }

    @Nullable
    public Annotation getAnnotationInstance() {
        return annotationInstance;
    }
}
