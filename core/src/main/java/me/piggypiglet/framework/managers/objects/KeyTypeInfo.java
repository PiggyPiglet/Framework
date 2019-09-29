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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Function;

public final class KeyTypeInfo {
    private final List<KeyFunction<?, ?>> keys;

    private KeyTypeInfo(List<KeyFunction<?, ?>> keys) {
        this.keys = keys;
    }

    public static Builder builder() {
        return new Builder();
    }

    public List<KeyFunction<?, ?>> getKeys() {
        return keys;
    }

    public static class Builder {
        private final List<KeyFunction<?, ?>> keys = new ArrayList<>();

        private Builder() {}

        public final <T> KeyFunctionBuilder<T, T> clazz(Class<T> clazz) {
            return clazz(clazz, s -> s);
        }

        public final <T, U> KeyFunctionBuilder<T, U> clazz(Class<T> clazz, Function<T, U> mapper) {
            return new KeyFunctionBuilder<>(clazz, mapper);
        }

        public final <T> KeyFunctionBuilder<T, T> interfaze(Class<T> interfaze) {
            return interfaze(interfaze, s -> s);
        }

        public final <T, U> KeyFunctionBuilder<T, U> interfaze(Class<T> interfaze, Function<T, U> mapper) {
            return new KeyFunctionBuilder<>(interfaze, mapper);
        }

        public class KeyFunctionBuilder<T, U> {
            private final Class<T> clazz;
            private final Function<T, U> mapper;
            private Function<U, Object> getter;
            private Function<U, Boolean> exists;

            private KeyFunctionBuilder(Class<T> clazz, Function<T, U> mapper) {
                this.clazz = clazz;
                this.mapper = mapper;
            }

            public final KeyFunctionBuilder<T, U> getter(Function<U, Object> getter) {
                this.getter = getter;
                return this;
            }

            public final KeyFunctionBuilder<T, U> exists(Function<U, Boolean> exists) {
                this.exists = exists;
                return this;
            }

            public final <O> Builder map(Map<U, O> map, O def) {
                return getter(o -> map.getOrDefault(o, def))
                        .exists(map::containsKey)
                        .bundle();
            }

            public final <O> Builder list(List<O> list, BiPredicate<U, O> filter, O def) {
                return new KeyFunctionBuilder<>(clazz, t -> list.stream().filter(o -> filter.test(mapper.apply(t), o)).findAny())
                        .getter(o -> o.orElse(def))
                        .exists(Optional::isPresent)
                        .bundle();
            }

            public final Builder bundle() {
                keys.add(new KeyFunction<>(clazz, mapper, getter, exists));
                return Builder.this;
            }
        }

        public KeyTypeInfo build() {
            return new KeyTypeInfo(keys);
        }
    }
}
