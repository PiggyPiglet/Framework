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

package me.piggypiglet.framework.file.framework.objects;

import me.piggypiglet.framework.utils.map.Maps;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

public final class Mapper {
    private final Class<?> clazz;
    private final Function<?, Map<String, Object>> function;

    private Mapper(Class<?> clazz, Function<?, Map<String, Object>> function) {
        this.clazz = clazz;
        this.function = function;
    }

    public static <T> Builder<T> builder(Class<T> clazz) {
        return new Builder<>(clazz);
    }

    public static class Builder<T> {
        private final Class<T> clazz;
        private Function<T, Map<String, Object>> function = null;

        private Builder(Class<T> clazz) {
            this.clazz = clazz;
        }

        public Builder<T> flattener(Function<T, Map<String, Object>> function) {
            this.function = function;
            return this;
        }

        public Mapper build() {
            return new Mapper(clazz, function);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> Stream<Map.Entry<String, Object>> flatten(Map.Entry<String, Object> entry) {
        if (clazz == null || function == null) {
            return Maps.flatten(entry);
        }

        return Maps.flatten(entry, (Class<T>) clazz, (Function<T, Map<String, Object>>) function);
    }

    @SuppressWarnings("unchecked")
    public <T> Class<T> getClazz() {
        return (Class<T>) clazz;
    }

    @SuppressWarnings("unchecked")
    public <T> Function<T, Map<String, Object>> getFunction() {
        return (Function<T, Map<String, Object>>) function;
    }
}
