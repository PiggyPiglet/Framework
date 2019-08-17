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

    /**
     * Create an instance of a third-party logger with a name.
     * @param name Name the logger will use
     * @return Instance of third-party logger
     */
    protected abstract T init(String name);

    /**
     * Implementation of sending an info message through a third-party logger.
     * @param message Message to be logged.
     */
    protected abstract void info(String message);

    /**
     * Implementation of sending a warning message through a third-party logger.
     * @param message Message to be logged.
     */
    protected abstract void warning(String message);

    /**
     * Implementation of sending an error message through a third-party logger.
     * @param message Message to be logged.
     */
    protected abstract void error(String message);

    /**
     * Create a logger instance with a name
     * @param name Name that will appear when logging.
     * @return Logger instance
     */
    public Logger create(String name) {
        logger = init(name);
        return this;
    }

    /**
     * Log an info message.
     * @param message Message to be logged
     * @param vars Any variables to be replaced in the message (uses String#format)
     */
    public void info(String message, Object... vars) {
        info(String.format(message, vars));
    }

    /**
     * Log a warning message.
     * @param message Message to be logged
     * @param vars Any variables to be replaced in the message (uses String#format)
     */
    public void warning(String message, Object... vars) {
        warning(String.format(message, vars));
    }

    /**
     * Log an error message.
     * @param message Message to be logged
     * @param vars Any variables to be replaced in the message (uses String#format)
     */
    public void error(String message, Object... vars) {
        error(String.format(message, vars));
    }
}
