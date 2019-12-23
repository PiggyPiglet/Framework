/*
 * MIT License
 *
 * Copyright (c) 2019 PiggyPiglet
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package me.piggypiglet.framework.logging;

import com.google.inject.ImplementedBy;
import me.piggypiglet.framework.logging.implementations.DefaultLogger;
import me.piggypiglet.framework.utils.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

@ImplementedBy(DefaultLogger.class)
public abstract class Logger<T> {
    private boolean debug;

    protected T underlyingLogger;

    /**
     * Create an instance of a third-party logger with a name.
     *
     * @param name Name the logger will use
     * @return Instance of third-party logger
     */
    protected abstract T init(String name);

    /**
     * Implementation of sending an info message through a third-party logger.
     *
     * @param message Message to be logged.
     */
    protected abstract void info(String message);

    /**
     * Implementation of sending a warning message through a third-party logger.
     *
     * @param message Message to be logged.
     */
    protected abstract void warning(String message);

    /**
     * Implementation of sending an error message through a third-party logger.
     *
     * @param message Message to be logged.
     */
    protected abstract void error(String message);

    /**
     * Implementation of sending a debug message through a third-party logger.
     *
     * @param message Message to be logged.
     */
    protected abstract void debug(String message);

    /**
     * Create a logger instance with a name
     *
     * @param name  Name that will appear when logging.
     * @param debug Whether debugging is enabled
     * @return Logger instance
     */
    public Logger<T> create(String name, boolean debug) {
        underlyingLogger = init(name);
        this.debug = debug;
        return this;
    }

    /**
     * Log an info message.
     *
     * @param message Message to be logged
     * @param vars    Any variables to be replaced in the message (uses String#format)
     */
    public void info(Object message, Object... vars) {
        info(format(message, vars));
    }

    /**
     * Log a warning message.
     *
     * @param message Message to be logged
     * @param vars    Any variables to be replaced in the message (uses String#format)
     */
    public void warning(Object message, Object... vars) {
        warning(format(message, vars));
    }

    /**
     * Log an error message.
     *
     * @param message Message to be logged
     * @param vars    Any variables to be replaced in the message (uses String#format)
     */
    public void error(Object message, Object... vars) {
        if (message instanceof Exception) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            PrintStream ps = new PrintStream(out);
            ((Exception) message).printStackTrace(ps);
            ps.close();
            error(out.toString());
        } else {
            error(format(message, vars));
        }
    }

    /**
     * Log a debug message.
     *
     * @param message Message to be logged.
     * @param vars    Any variables to be replaced in the message (uses String#format)
     */
    public void debug(Object message, Object... vars) {
        if (debug) {
            debug(format(message, vars));
        }
    }

    private String format(Object message, Object... concatenations) {
        return StringUtils.format(message, concatenations);
    }
}
