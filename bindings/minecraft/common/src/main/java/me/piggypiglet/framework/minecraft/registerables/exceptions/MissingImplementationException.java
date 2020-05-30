package me.piggypiglet.framework.minecraft.registerables.exceptions;

import org.jetbrains.annotations.NotNull;

public final class MissingImplementationException extends RuntimeException {
    public MissingImplementationException(@NotNull final String message) {
        super(message);
    }
}
