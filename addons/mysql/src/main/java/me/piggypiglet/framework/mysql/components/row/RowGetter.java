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

package me.piggypiglet.framework.mysql.components.row;

import co.aikar.idb.DbRow;
import me.piggypiglet.framework.mysql.components.MySQLComponent;
import me.piggypiglet.framework.utils.map.Maps;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public final class RowGetter extends MySQLComponent {
    private final String table;
    private final Map<String, Object> location;

    private RowGetter(String table, Map<String, Object> location) {
        this.table = table;
        this.location = location;
    }

    public static Builder builder(String table) {
        return new Builder(table);
    }

    public static class Builder {
        private final String table;
        private Map<String, Object> location;

        private Builder(String table) {
            this.table = table;
        }

        /**
         * Location of the row(s) to find
         * @return Builder instance
         */
        public Maps.Builder<String, Object, Builder, Object> location() {
            return Maps.builder(new LinkedHashMap<>(), this, m -> location = m);
        }

        /**
         * Location of the row(s) to find
         * @param location Row location
         * @return Builder instance
         */
        public Builder location(Map<String, Object> location) {
            this.location = location;
            return this;
        }

        /**
         * Build the row getter
         * @return RowGetter
         */
        public RowGetter build() {
            return new RowGetter(table, location);
        }
    }

    /**
     * Get a specific row from the location
     * @return CompletableFuture of DbRow
     */
    public CompletableFuture<DbRow> get() {
        return get(table, location);
    }

    /**
     * Get all rows in the table
     * @return CompletableFuture List of DbRows
     */
    public CompletableFuture<List<DbRow>> getAll() {
        return getAll(table);
    }

    /**
     * Check if the row exists
     * @return CompletableFuture of boolean
     */
    public CompletableFuture<Boolean> exists() {
        return exists(table, location);
    }
}