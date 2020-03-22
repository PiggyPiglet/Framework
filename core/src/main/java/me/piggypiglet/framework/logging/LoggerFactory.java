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

package me.piggypiglet.framework.logging;

import com.google.inject.Inject;
import me.piggypiglet.framework.Framework;
import me.piggypiglet.framework.init.bootstrap.FrameworkBootstrap;
import me.piggypiglet.framework.logging.framework.Logger;
import me.piggypiglet.framework.logging.implementations.DefaultLogger;
import me.piggypiglet.framework.logging.internal.LoggerManager;
import me.piggypiglet.framework.utils.annotations.internal.Internal;

public final class LoggerFactory {
    @Inject private static FrameworkBootstrap main;
    @Inject private static Framework framework;
    @Inject @Internal("logger_class") private static Class<? extends Logger<?>> logger;
    @Inject private static LoggerManager loggerManager;

    private LoggerFactory() {
        throw new RuntimeException("LoggerFactory class should not be instantiated.");
    }

    /**
     * Get an instance using the set logger implementation. Will return
     * DefaultLogger (implementation of Java Logger) if no custom loggers have been made.
     * <p>
     * Prefer to inject a logger instead of using this function, as it relies on static
     * injections.
     *
     * @param name Name of the logger
     * @return Logger instance
     */
    public static Logger<?> getLogger(String name) {
        final boolean debug = framework.isDebug();

        if (loggerManager.exists(name)) {
            return loggerManager.get(name);
        }

        try {
            return main.getInjector().getInstance(logger).create(name, debug);
        } catch (Exception ignored) {}

        return new DefaultLogger().create(name, debug);
    }
}
