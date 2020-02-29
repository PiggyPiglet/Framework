/*
 * MIT License
 *
 * Copyright (c) 2019-2020 PiggyPiglet
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

package me.piggypiglet.framework.logging.framework;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static me.piggypiglet.framework.utils.StringUtils.format;

public abstract class Logger<T> {
    private T handle;
    private String name;
    private boolean debug;

    @NotNull
    protected abstract T init(@NotNull final String name);

    protected abstract void info(@NotNull final T logger, @NotNull final String message);

    protected abstract void warning(@NotNull final T logger, @NotNull final String message);

    protected abstract void error(@NotNull final T logger, @NotNull final String message);

    protected abstract void debug(@NotNull final T logger, @NotNull final String message);

    @NotNull
    public Logger<T> create(@NotNull final String name, final boolean debug) {
        handle = init(name);
        this.name = name;
        this.debug = debug;
        return this;
    }

    public void info(@NotNull final Object message, @NotNull final Object... vars) {
        info(handle, format(message, vars));
    }

    public void warning(@NotNull final Object message, @NotNull final Object... vars) {
        warning(handle, format(message, vars));
    }

    public void error(@NotNull final Object message, @NotNull final Object... vars) {
        if (message instanceof Throwable) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            PrintStream ps = new PrintStream(out);
            ((Exception) message).printStackTrace(ps);
            ps.close();
            error(handle, out.toString());
        } else {
            error(handle, format(message, vars));
        }
    }

    public void debug(@NotNull final Object message, @NotNull final Object... vars) {
        if (debug) {
            debug(format(message, vars));
        }
    }

    @NotNull
    public T getHandle() {
        return handle;
    }

    @NotNull
    public String getName() {
        return name;
    }

    public boolean isDebug() {
        return debug;
    }
}
