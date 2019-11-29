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

package me.piggypiglet.framework.mysql.components.row;

import me.piggypiglet.framework.mysql.components.MySQLComponent;
import me.piggypiglet.framework.utils.map.Maps;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public final class RowCreator extends MySQLComponent {
    private final String table;
    private final Map<String, Object> data;

    private RowCreator(String table, Map<String, Object> data) {
        this.table = table;
        this.data = data;
    }

    public static Builder builder(String table) {
        return new Builder(table);
    }

    public static class Builder {
        private final String table;

        private Map<String, Object> data = null;

        private Builder(String table) {
            this.table = table;
        }

        public Maps.Builder<String, Object, Builder> data() {
            return Maps.builder(new LinkedHashMap<>(), this, m -> data = m);
        }

        public Builder data(Map<String, Object> data) {
            this.data = data;
            return this;
        }

        /**
         * Build the RowCreator
         * @return RowCreator
         */
        public RowCreator build() {
            return new RowCreator(table, data);
        }
    }

    /**
     * Create the row
     * @return CompletableFuture on whether the operation was successful
     */
    public CompletableFuture<Boolean> execute() {
        return create(table, data);
    }
}