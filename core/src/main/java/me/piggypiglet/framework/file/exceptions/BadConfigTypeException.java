package me.piggypiglet.framework.file.exceptions;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------

/**
 * Thrown if the implementation of AbstractFileConfiguration is incorrectly made
 */
public final class BadConfigTypeException extends Exception {
    public BadConfigTypeException(String message) {
        super(message);
    }
}
