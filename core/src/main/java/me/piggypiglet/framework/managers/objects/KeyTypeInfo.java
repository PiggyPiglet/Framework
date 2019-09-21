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
import java.util.function.Function;

public final class KeyTypeInfo {
    private final List<KeyFunction<?>> keys;

    private KeyTypeInfo(List<KeyFunction<?>> keys) {
        this.keys = keys;
    }

    public static Builder builder() {
        return new Builder();
    }

    public List<KeyFunction<?>> getKeys() {
        return keys;
    }

    public static class Builder {
        private final List<KeyFunction<?>> keys = new ArrayList<>();

        private Builder() {}

        public final <T> Builder clazz(Class<T> clazz, Function<T, Object> getter) {
            keys.add(new KeyFunction<>(clazz, getter, true));
            return this;
        }

        public final <T> Builder interfaze(Class<T> interfaze, Function<T, Object> getter) {
            keys.add(new KeyFunction<>(interfaze, getter, false));
            return this;
        }

        public KeyTypeInfo build() {
            return new KeyTypeInfo(keys);
        }
    }
}
