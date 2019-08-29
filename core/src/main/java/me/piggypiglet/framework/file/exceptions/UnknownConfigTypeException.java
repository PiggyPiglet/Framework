package me.piggypiglet.framework.file.exceptions;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------

/**
 * Thrown if no config could be found for the path/file provided
 */
public final class UnknownConfigTypeException extends Exception {
    public UnknownConfigTypeException(String message) {
        super(message);
    }
}