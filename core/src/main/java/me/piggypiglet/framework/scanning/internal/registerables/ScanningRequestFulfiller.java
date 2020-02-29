package me.piggypiglet.framework.scanning.internal.registerables;

import com.google.inject.Inject;
import com.google.inject.TypeLiteral;
import me.piggypiglet.framework.Framework;
import me.piggypiglet.framework.registerables.StartupRegisterable;
import me.piggypiglet.framework.scanning.framework.Scanner;
import me.piggypiglet.framework.scanning.internal.ScanningKeys;
import me.piggypiglet.framework.scanning.internal.objects.ScanningRequest;
import me.piggypiglet.framework.utils.annotations.wrapper.AnnotationWrapper;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public final class ScanningRequestFulfiller extends StartupRegisterable {
    @Inject private Framework framework;
    @Inject private Scanner scanner;

    @Override
    protected void execute() {
        final Set<ScanningRequest> requests = Arrays.stream(ScanningKeys.values()).map(ScanningKeys::getRequest).collect(Collectors.toSet());
        requests.addAll(framework.getScanning().getRequests());
        requests.forEach(r -> bind(TypeLiteral.get(r.getBindType()), r.getAnnotation(), r.getFunction().apply(scanner)));
    }

    @SuppressWarnings("unchecked")
    private <T> void bind(@NotNull final TypeLiteral<T> type, @NotNull final AnnotationWrapper annotation,
                          @NotNull final Set<?> set) {
        addAnnotatedBinding(type, annotation, (T) set);
    }
}
