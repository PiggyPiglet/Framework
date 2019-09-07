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

package me.piggypiglet.framework.mysql.components.row.objects;

import java.util.ArrayList;
import java.util.List;

public final class KeyValueSet {
    private final List<String> keys;
    private final List<Object> values;

    private KeyValueSet(List<String> keys, List<Object> values) {
        this.keys = keys;
        this.values = values;
    }

    /**
     * Get an instance of the builder
     * @return Builder instance
     */
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final List<String> keys = new ArrayList<>();
        private final List<Object> values = new ArrayList<>();

        /**
         * Key for values
         * @param key Key
         * @return Builder instance
         */
        public Builder key(String key) {
            keys.add(key);
            return this;
        }

        /**
         * Value for keys, in order
         * @param value Value
         * @return Builder instance
         */
        public Builder value(Object value) {
            values.add(value);
            return this;
        }

        /**
         * Build the KeyValueSet
         * @return KeyValueSet
         */
        public KeyValueSet build() {
            return new KeyValueSet(keys, values);
        }
    }

    public String[] getKeys() {
        return keys.toArray(new String[]{});
    }

    public Object[] getValues() {
        return values.toArray();
    }
}