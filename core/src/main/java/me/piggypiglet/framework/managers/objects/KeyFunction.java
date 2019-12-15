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

import java.util.function.Function;
import java.util.function.Predicate;

public final class KeyFunction<T, U> {
    private final Class<T> type;
    private final Function<T, U> mapper;
    private final Function<U, Object> getter;
    private final Predicate<U> exists;

    public KeyFunction(Class<T> type, Function<T, U> mapper, Function<U, Object> getter, Predicate<U> exists) {
        this.type = type;
        this.mapper = mapper;
        this.getter = getter;
        this.exists = exists;
    }

    public Class<T> getType() {
        return type;
    }

    public Function<T, U> getMapper() {
        return mapper;
    }

    public Function<U, Object> getGetter() {
        return getter;
    }

    public Predicate<U> getExists() {
        return exists;
    }
}
