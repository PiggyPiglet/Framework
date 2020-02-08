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

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Generic builder class that can be used on any object, that has setters.
 */
public final class GenericBuilder {
    private GenericBuilder() {
        throw new RuntimeException("This class cannot be instantiated.");
    }

    /**
     * Get a builder instance for an object.
     *
     * @param initializer Object instantiator
     * @param <T>         Type generic of object
     * @return Generic Object Builder
     */
    public static <T> Builder<T> of(Supplier<T> initializer) {
        return new Builder<>(initializer);
    }

    public static class Builder<T> {
        private final Supplier<T> initializer;
        private final List<Consumer<T>> instanceModifiers = new ArrayList<>();

        private Builder(Supplier<T> initializer) {
            this.initializer = initializer;
        }

        /**
         * Method to execute a void method
         *
         * @param consumer Void method
         * @return Generic Object Builder
         */
        public Builder<T> with(Consumer<T> consumer) {
            instanceModifiers.add(consumer);
            return this;
        }

        /**
         * Method to supply a setter method with it's value.
         *
         * @param consumer Apply value to setter logic
         * @param value    Value
         * @param <U>      Value type
         * @return Generic Object Builder
         */
        public <U> Builder<T> with(BiConsumer<T, U> consumer, U value) {
            instanceModifiers.add(instance -> consumer.accept(instance, value));
            return this;
        }

        /**
         * Build the object, after initialising with the instantiator and applying setters.
         *
         * @return Object
         */
        public T build() {
            T value = initializer.get();
            instanceModifiers.forEach(modifier -> modifier.accept(value));
            instanceModifiers.clear();
            return value;
        }
    }
}
