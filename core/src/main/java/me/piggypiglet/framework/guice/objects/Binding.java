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

package me.piggypiglet.framework.guice.objects;

import com.google.inject.TypeLiteral;

// ------------------------------
// Copyright (c) PiggyPiglet 2020
// https://www.piggypiglet.me
// ------------------------------
public final class Binding<T> {
    private final Object type;
    private final T instance;
    private final boolean typeLiteral;

    public Binding(TypeLiteral<? super T> type, T instance) {
        this((Object) type, instance);
    }

    public Binding(Class<? super T> interfaze, T instance) {
        this((Object) interfaze, instance);
    }

    private Binding(Object type, T instance) {
        this.type = type;
        this.instance = instance;
        typeLiteral = type instanceof TypeLiteral;
    }

    @SuppressWarnings("unchecked")
    public TypeLiteral<? super T> getTypeLiteral() {
        return (TypeLiteral<? super T>) type;
    }

    @SuppressWarnings("unchecked")
    public Class<? super T> getTypeClass() {
        return (Class<? super T>) type;
    }

    public T getInstance() {
        return instance;
    }

    public boolean isTypeLiteral() {
        return typeLiteral;
    }
}
