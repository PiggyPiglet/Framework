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

package me.piggypiglet.framework.utils.map;

import java.util.HashMap;
import java.util.Map;

public final class KeyValueSet {
    private final Map<String, Object> map;

    private KeyValueSet(Map<String, Object> map) {
        this.map = map;
    }

    /**
     * Get an instance of the builder
     * @return Builder instance
     */
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        Map<String, Object> values = new HashMap<>();

        /**
         * Key for values
         * @param key Key
         * @return Builder instance
         */
        public ValueBuilder key(String key) {
            return new ValueBuilder(key);
        }

        public class ValueBuilder {
            private final String key;

            private ValueBuilder(String key) {
                this.key = key;
            }

            /**
             * Value for keys, in order
             * @param value Value
             * @return Builder instance
             */
            public Builder value(Object value) {
                values.put(key, value);
                return Builder.this;
            }
        }

        /**
         * Build the KeyValueSet
         * @return KeyValueSet
         */
        public KeyValueSet build() {
            return new KeyValueSet(values);
        }
    }

    public String[] getKeys() {
        return map.keySet().toArray(new String[]{});
    }

    public Object[] getValues() {
        return map.values().toArray();
    }

    public Map<String, Object> getMap() {
        return map;
    }
}