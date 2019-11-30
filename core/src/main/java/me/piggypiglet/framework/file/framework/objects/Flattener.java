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

package me.piggypiglet.framework.file.framework.objects;

import me.piggypiglet.framework.utils.map.Maps;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

public final class Flattener {
    private final Class<?> clazz;
    private final Function<?, Map<String, Object>> flattener;

    private Flattener(Class<?> clazz, Function<?, Map<String, Object>> flattener) {
        this.clazz = clazz;
        this.flattener = flattener;
    }

    public static <T> Builder<T> builder(Class<T> clazz) {
        return new Builder<>(clazz);
    }

    public static class Builder<T> {
        private final Class<T> clazz;
        private Function<T, Map<String, Object>> flattener = null;

        private Builder(Class<T> clazz) {
            this.clazz = clazz;
        }

        public Builder<T> flattener(Function<T, Map<String, Object>> flattener) {
            this.flattener = flattener;
            return this;
        }

        public Flattener build() {
            return new Flattener(clazz, flattener);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> Stream<Map.Entry<String, Object>> flatten(Map.Entry<String, Object> entry) {
        if (clazz == null || flattener == null) {
            return Maps.flatten(entry);
        }

        return Maps.flatten(entry, (Class<T>) clazz, (Function<T, Map<String, Object>>) flattener);
    }
}
