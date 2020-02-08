package me.piggypiglet.framework.scanning.exceptions;

import javax.annotation.Nonnull;

/**
 * Thrown if a fatal exception occurs in a scanner implementation.
 */
public final class ScannerException extends RuntimeException {
    public ScannerException(@Nonnull final String message) {
        super(message);
    }
}
