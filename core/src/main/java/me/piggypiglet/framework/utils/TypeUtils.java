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

package me.piggypiglet.framework.utils;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class TypeUtils {
    private TypeUtils() {
        throw new RuntimeException("This class cannot be instantiated.");
    }

    /**
     * Check if a value is null, if it is, return an optional def, if def isn't provided, return a configurable null value
     * @param value     Value
     * @param nullValue Default null value if def &amp; value is null
     * @param def       Default value if null
     * @param <T>       Type of value
     * @return value, nullValue, or def
     */
    @Contract("_, _, !null -> !null; _, !null, _ -> !null")
    @Nullable
    @SafeVarargs
    public static <T> T valueNullDef(@Nullable final T value, @Nullable T nullValue, @NotNull T... def) {
        if (value != null) {
            return value;
        }

        return def.length >= 1 ? def[0] : nullValue;
    }
}
