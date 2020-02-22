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

import me.piggypiglet.framework.mysql.components.MySQLComponent;
import me.piggypiglet.framework.utils.map.Maps;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public final class RowEditor extends MySQLComponent {
    private final String table;
    private final Map<String, Object> location;
    private final Map<String, Object> changes;

    private RowEditor(String table, Map<String, Object> location, Map<String, Object> changes) {
        this.table = table;
        this.location = location;
        this.changes = changes;
    }

    public static Builder builder(String table) {
        return new Builder(table);
    }

    public static class Builder {
        private final String table;
        private Map<String, Object> location;
        private Map<String, Object> changes;

        private Builder(String table) {
            this.table = table;
        }

        /**
         * Location of the row
         * @return Builder instance
         */
        public Maps.Builder<String, Object, ?, Builder> location() {
            return Maps.builder(new LinkedHashMap<>(), m -> {
                location = m;
                return this;
            });
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
         * Changes to make
         * @return Builder instance
         */
        public Maps.Builder<String, Object, ?, Builder> changes() {
            return Maps.builder(new LinkedHashMap<>(), m -> {
                changes = m;
                return this;
            });
        }

        /**
         * Location of the row(s) to find
         * @param changes Row changes
         * @return Builder instance
         */
        public Builder changes(Map<String, Object> changes) {
            this.changes = changes;
            return this;
        }

        /**
         * Build the RowEditor
         * @return RowEditor
         */
        public RowEditor build() {
            return new RowEditor(table, location, changes);
        }
    }

    /**
     * Execute the edit
     * @return CompletableFuture of whether the operation was successful.
     */
    public CompletableFuture<Boolean> execute() {
        return edit(table, location, changes);
    }
}