package me.piggypiglet.framework.init.builder.stages.scanning;

import me.piggypiglet.framework.scanning.Scanners;
import me.piggypiglet.framework.scanning.builders.ScannerBuilder;
import me.piggypiglet.framework.scanning.framework.Scanner;
import me.piggypiglet.framework.scanning.internal.objects.ScanningRequest;
import me.piggypiglet.framework.utils.annotations.wrapper.AnnotationWrapper;
import me.piggypiglet.framework.utils.builder.AbstractBuilder;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

public final class ScanningBuilder<R> extends AbstractBuilder<ScanningData, R> {
    private final Class<?> main;

    private final Set<ScanningRequest> requests = new HashSet<>();
    private Scanner scanner = null;

    public ScanningBuilder(@NotNull final Class<?> main) {
        this.main = main;
    }

    @NotNull
    public ScannerBuilder<ScanningBuilder<R>> scanner() {
        return Scanners.builder(main, scanner -> {
            this.scanner = scanner;
            return this;
        });
    }

    @NotNull
    public ScanningBuilder<R> scanner(@NotNull final Scanner scanner) {
        this.scanner = scanner;
        return this;
    }

    @NotNull
    public ScanningBuilder<R> request(@NotNull final Class<? extends Annotation> annotation, @NotNull final Type type,
                                          @NotNull final Function<Scanner, Set<?>> function) {
        return request(new AnnotationWrapper(annotation), type, function);
    }

    @NotNull
    public ScanningBuilder<R> request(@NotNull final Annotation annotation, @NotNull final Type type,
                                          @NotNull final Function<Scanner, Set<?>> function) {
        return request(new AnnotationWrapper(annotation), type, function);
    }

    @NotNull
    private ScanningBuilder<R> request(@NotNull final AnnotationWrapper annotation, @NotNull final Type type,
                                           @NotNull final Function<Scanner, Set<?>> function) {
        requests.add(new ScanningRequest(annotation, type, function));
        return this;
    }

    @Override
    protected ScanningData provideBuild() {
        return new ScanningData(requests, scanner);
    }
}
