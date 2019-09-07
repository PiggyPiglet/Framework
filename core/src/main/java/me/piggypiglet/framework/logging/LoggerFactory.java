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

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.name.Named;
import me.piggypiglet.framework.logging.implementations.DefaultLogger;

public final class LoggerFactory {
    @Inject private static Injector injector;
    @Inject @Named("logger") private static Class logger;

    /**
     * Get an instance using the set logger implementation. Will return DefaultLogger (implementation of Java Logger) if no custom loggers have been made.
     * @param name Name of the logger
     * @return Logger instance
     */
    @SuppressWarnings("unchecked")
    public static Logger getLogger(String name) {
        try {
            return ((Logger) injector.getInstance(logger)).create(name);
        } catch (Exception ignored) {}

        return new DefaultLogger().create(name);
    }
}
