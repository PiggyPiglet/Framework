package me.piggypiglet.framework.logging;

import com.google.inject.ImplementedBy;
import me.piggypiglet.framework.logging.implementations.DefaultLogger;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
@ImplementedBy(DefaultLogger.class)
public abstract class Logger<T> {
    protected T logger;

    protected abstract T init(String name);

    protected abstract void info(String message);

    protected abstract void warning(String message);

    protected abstract void error(String message);

    public Logger create(String name) {
        logger = init(name);
        return this;
    }

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
