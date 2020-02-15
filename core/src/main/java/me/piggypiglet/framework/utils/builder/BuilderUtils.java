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

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Small suite of utilities especially related to the preconditions of
 * compiling a builder's components, and the embeddability of builders.
 */
public final class BuilderUtils {
    private BuilderUtils() {
        throw new UnsupportedOperationException("This class cannot be initialized.");
    }

    /**
     * Function that will check if a builder has the required variables set,
     * and if not, throw a RuntimeException.
     *
     * @param builderName Name of the builder, used in the exception method
     * @param variables   Variables that need to be set
     */
    public static void requiredVariables(@NotNull final String builderName, @NotNull final Object... variables) {
        handleVariables(unsetVariables -> {
            throw new UnsetVarsException("These required variables weren't set in your " + builderName + ": " + unsetVariables);
        }, variables);
    }

    /**
     * Function that will check if suggested variables have been set, and
     * if not, log a warning.
     *
     * @param builderName Name of the builder, used in the warning log
     * @param variables   Suggested variables
     */
    public static void warningVariables(@NotNull final String builderName, @NotNull final Object... variables) {
        handleVariables(unsetVariables -> System.out.println(String.format("These suggested variables weren't set in your %s: %s", builderName, unsetVariables)),
                variables);
    }

    /**
     * Function for base functionality of requiredVariables and
     * warningVariables. Streams through an array of objects and check's,
     * assuming that they're an instance of String, starts with "d-". It then
     * collect's the matches, remove's the "d-" prefix from each, and uses the
     * remaining string as a variable name, joining the array with ", ". This joint
     * array is then provided in the defaultLogic consumer, assuming the joint
     * array isn't an empty string.
     * <p>
     * In a nutshell, in your builders, define you variables as Objects and give
     * them a default value of "d-variable name", for example, if you require a class,
     * Object clazz = "d-clazz";
     * "clazz" being the friendly name that may be provided in the consumer.
     *
     * @param defaultLogic Logic to run if variables have their default value.
     * @param variables    Array of variables to check
     */
    public static void handleVariables(@NotNull final Consumer<String> defaultLogic, @NotNull final Object... variables) {
        final String unsetVariables = Arrays.stream(variables).filter(o -> {
            try {
                return ((String) o).startsWith("d-");
            } catch (Exception e) {
                return false;
            }
        }).map(String::valueOf).map(s -> s.replaceFirst("d-", "")).collect(Collectors.joining(", "));

        if (!unsetVariables.isEmpty())
            defaultLogic.accept(unsetVariables);
    }

    /**
     * Function that will effectively wrap around a builder to alter
     * it's build return value/type. This is especially useful for
     * embeddable builders. For example, a root builder with child
     * builders, where the child builders' build methods return the
     * master builder instance, but the child builders can still be
     * used standalone.
     *
     * @param builder Builder instance
     * @param build   Build method logic
     * @param <B>     Builder type extending AbstractBuilder
     * @param <D>     Type the builder returns by default
     * @param <R>     New return type
     * @return original builder
     */
    @NotNull
    public static <B extends AbstractBuilder<D, R>, D, R> B customBuilder(@NotNull final B builder, @NotNull final Function<D, R> build) {
        builder.setBuilder(build);
        return builder;
    }
}
