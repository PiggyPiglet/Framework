package me.piggypiglet.framework.logging;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public abstract class Logger<T> {
    protected final T logger;

    public Logger(T logger) {
        this.logger = logger;
    }

    protected abstract void info(String message);

    protected abstract void warning(String message);

    protected abstract void error(String message);

    public void info(String message, Object... vars) {
        info(String.format(message, vars));
    }

    public void warning(String message, Object... vars) {
        warning(String.format(message, vars));
    }

    public void error(String message, Object... vars) {
        error(String.format(message, vars));
    }
}
