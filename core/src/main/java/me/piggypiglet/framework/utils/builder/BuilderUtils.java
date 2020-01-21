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

package me.piggypiglet.framework.utils.builder;

import java.util.Arrays;
import java.util.stream.Collectors;

public final class BuilderUtils {
    private BuilderUtils() {
        throw new RuntimeException("This class cannot be instantiated.");
    }

    /**
     * Utility method that will check if a builder has the required variables set,
     * and if not, throw a RuntimeException.
     *
     * @param builderName Name of the builder, used in the exception method
     * @param vars        Variables that need to be set
     */
    public static void checkVars(String builderName, Object... vars) {
        final String unsetVars = Arrays.stream(vars).filter(o -> {
            try {
                return ((String) o).startsWith("d-");
            } catch (Exception e) {
                return false;
            }
        }).map(String::valueOf).map(s -> s.replaceFirst("d-", "")).collect(Collectors.joining(", "));

        if (!unsetVars.isEmpty())
            throw new UnsetVarsException("These required vars weren't set in your " + builderName + ": " + unsetVars);
    }
}
