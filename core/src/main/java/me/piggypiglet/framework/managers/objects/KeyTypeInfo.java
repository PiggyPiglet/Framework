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

package me.piggypiglet.framework.managers.objects;

import me.piggypiglet.framework.utils.lambda.NullableFunction;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public final class KeyTypeInfo {
    private final List<KeyFunction<?, ?>> keys;

    private KeyTypeInfo(List<KeyFunction<?, ?>> keys) {
        this.keys = keys;
    }

    /**
     * Get an instance of KeyTypeInfo.Builder
     *
     * @return Builder instance
     */
    public static Builder builder() {
        return new Builder();
    }

    public List<KeyFunction<?, ?>> getKeys() {
        return keys;
    }

    public static class Builder {
        private final List<KeyFunction<?, ?>> keys = new ArrayList<>();

        private Builder() {
        }

        /**
         * Begin the process of creating a keyfunction with a class/interface as the key
         *
         * @param key Key class
         * @param <T> Type generic of key class
         * @return KeyFunctionBuilder
         */
        public final <T> KeyFunctionBuilder<T, T> key(Class<T> key) {
            return key(key, s -> s);
        }

        /**
         * Begin the process of creating a keyfunction with a class/interface as the key, and a mapper to convert the key to something else
         *
         * @param key    Key class
         * @param mapper Optional mapping of key
         * @param <T>    Type generic of key class
         * @param <U>    Type generic of the mapped key
         * @return KeyFunctionBuilder
         */
        public final <T, U> KeyFunctionBuilder<T, U> key(Class<T> key, NullableFunction<T, U> mapper) {
            return new KeyFunctionBuilder<>(key, mapper);
        }

        public class KeyFunctionBuilder<T, U> {
            private final Class<T> key;
            private final NullableFunction<T, U> mapper;
            private NullableFunction<U, Object> getter;
            private Predicate<U> exists;

            private KeyFunctionBuilder(Class<T> key, NullableFunction<T, U> mapper) {
                this.key = key;
                this.mapper = mapper;
            }

            /**
             * Set the function to get from a data structure via the mapped key
             *
             * @param getter Function to get from data structure
             * @return Current instance of KeyFunctionBuilder
             */
            public final KeyFunctionBuilder<T, U> getter(NullableFunction<U, Object> getter) {
                this.getter = getter;
                return this;
            }

            /**
             * Set the function to check if something exists in a data structure via the mapped key
             *
             * @param exists Function to check if &lt;U&gt; exists (or what it represents) in a data structure
             * @return Current instance of KeyFunctionBuilder
             */
            public final KeyFunctionBuilder<T, U> exists(Predicate<U> exists) {
                this.exists = exists;
                return this;
            }

            /**
             * Util to build a KeyFunction based on a map data structure
             *
             * @param map Map instance
             * @param <O> Value type of map
             * @return Parent KeyTypeInfo.Builder instance
             */
            public <O> Builder map(Map<U, O> map) {
                return getter(map::get)
                        .exists(map::containsKey)
                        .bundle();
            }

            /**
             * Util to build a KeyFunction based on a map data structure,
             * with a default value if the key doesn't exist.
             *
             * @param map Map instance
             * @param def Default value
             * @param <O> Value type of map
             * @return Parent KeyTypeInfo.Builder instance
             */
            public final <O> Builder map(Map<U, O> map, O def) {
                return getter(o -> map.getOrDefault(o, def))
                        .exists(map::containsKey)
                        .bundle();
            }

            /**
             * Util to build a KeyFunction based on the list data structure
             *
             * @param list   List instance
             * @param filter Filter to find the relevant value based on U
             * @param def    Default value
             * @param <O>    Unknown value type of list
             * @return Parent KeyTypeInfo.Builder instance
             */
            public final <O> Builder list(List<O> list, BiPredicate<U, O> filter, O def) {
                return new KeyFunctionBuilder<>(key, t -> list.stream().filter(o -> filter.test(mapper.apply(t), o)).findAny())
                        .getter(o -> o.orElse(def))
                        .exists(Optional::isPresent)
                        .bundle();
            }

            /**
             * Bundle the getter &amp; exists functions into a KeyFunction
             *
             * @return Parent KeyTypeInfo.Builder instance
             */
            public final Builder bundle() {
                keys.add(new KeyFunction<>(key, mapper, getter, exists));
                return Builder.this;
            }
        }

        /**
         * Build the KeyTypeInfo instance from the data submitted into the builder.
         *
         * @return KeyTypeInfo instance
         */
        public KeyTypeInfo build() {
            return new KeyTypeInfo(keys);
        }
    }
}
