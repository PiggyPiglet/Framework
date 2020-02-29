package me.piggypiglet.framework.utils.annotations.internal;

import com.google.auto.value.AutoAnnotation;
import org.jetbrains.annotations.NotNull;

public final class InternalAnnotations {
    @AutoAnnotation
    public static Internal internal(@NotNull final String value) {
        return new AutoAnnotation_InternalAnnotations_internal(value);
    }
}
