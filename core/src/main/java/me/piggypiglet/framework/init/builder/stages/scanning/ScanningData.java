package me.piggypiglet.framework.init.builder.stages.scanning;

import me.piggypiglet.framework.scanning.framework.Scanner;
import me.piggypiglet.framework.scanning.internal.objects.ScanningRequest;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public final class ScanningData {
    private final Set<ScanningRequest> requests;
    private final Scanner scanner;

    ScanningData(@NotNull final Set<ScanningRequest> requets, @NotNull final Scanner scanner) {
        this.requests = requets;
        this.scanner = scanner;
    }

    @NotNull
    public Set<ScanningRequest> getRequests() {
        return requests;
    }

    @NotNull
    public Scanner getScanner() {
        return scanner;
    }
}
