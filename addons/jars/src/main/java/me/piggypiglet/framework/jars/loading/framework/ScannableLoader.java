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

package me.piggypiglet.framework.jars.loading.framework;

import me.piggypiglet.framework.utils.annotations.reflection.Disabled;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

@Disabled
public abstract class ScannableLoader<T> extends Loader {
    private final Predicate<Class<?>> match;

    private final List<T> predefined = new ArrayList<>();

    protected final Options options = new Options();

    protected ScannableLoader(String dir, Predicate<Class<?>> match) {
        super(dir);
        this.match = match;
    }

    protected abstract void init(T instance);

    @SuppressWarnings("unchecked")
    public void run(Object instance) {
        init((T) instance);
    }

    public void preRun() {
        predefined.forEach(this::init);
    }

    public Predicate<Class<?>> getMatch() {
        return match;
    }

    protected class Options {
        @SafeVarargs
        public final Options predefined(T... predefined) {
            ScannableLoader.this.predefined.addAll(Arrays.asList(predefined));
            return this;
        }
    }
}
