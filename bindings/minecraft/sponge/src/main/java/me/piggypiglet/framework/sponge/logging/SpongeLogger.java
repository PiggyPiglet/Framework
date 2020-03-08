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

package me.piggypiglet.framework.sponge.logging;

import com.google.inject.Inject;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

public final class SpongeLogger extends me.piggypiglet.framework.logging.framework.Logger<Logger> {
    @Inject private Logger logger;

    @NotNull
    @Override
    protected Logger init(@NotNull final String name) {
        return logger;
    }

    @Override
    protected void info(@NotNull final Logger logger, @NotNull final String message) {
        logger.info(message);
    }

    @Override
    protected void warning(@NotNull final Logger logger, @NotNull final String message) {
        logger.warn(message);
    }

    @Override
    protected void error(@NotNull final Logger logger, @NotNull final String message) {
        logger.error(message);
    }

    @Override
    protected void debug(@NotNull final Logger logger, @NotNull final String message) {
        logger.debug(message);
    }
}
