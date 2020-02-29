package me.piggypiglet.framework.scanning.internal.objects;

import me.piggypiglet.framework.scanning.framework.Scanner;
import me.piggypiglet.framework.utils.annotations.wrapper.AnnotationWrapper;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.util.Set;
import java.util.function.Function;

public final class ScanningRequest {
    private final AnnotationWrapper annotation;
    private final Type bindType;
    private final Function<Scanner, Set<?>> function;

    public ScanningRequest(@NotNull final AnnotationWrapper annotation, @NotNull final Type returnType,
                           @NotNull final Function<Scanner, Set<?>> function) {
        this.annotation = annotation;
        this.bindType = returnType;
        this.function = function;
    }

    @NotNull
    public AnnotationWrapper getAnnotation() {
        return annotation;
    }

    @NotNull
    public Type getBindType() {
        return bindType;
    }

    @NotNull
    public Function<Scanner, Set<?>> getFunction() {
        return function;
    }
}
