package me.piggypiglet.framework.scanning.exceptions;

import org.jetbrains.annotations.NotNull;

/**
 * Thrown if a fatal exception occurs in a scanner implementation.
 */
public final class ScannerException extends RuntimeException {
    public ScannerException(@NotNull final String message) {
        super(message);
    }
}
