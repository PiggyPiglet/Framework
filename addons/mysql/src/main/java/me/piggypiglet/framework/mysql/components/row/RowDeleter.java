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
import me.piggypiglet.framework.utils.map.KeyValueSet;

import java.util.concurrent.CompletableFuture;

public final class RowDeleter extends MySQLComponent {
    private final String table;
    private final KeyValueSet location;

    private RowDeleter(String table, KeyValueSet location) {
        this.table = table;
        this.location = location;
    }

    public static Builder builder(String table) {
        return new Builder(table);
    }

    public static class Builder {
        private final String table;
        private KeyValueSet location;

        private Builder(String table) {
            this.table = table;
        }

        /**
         * Specify a location for the row
         * @param location Location
         * @return Builder instance
         */
        public Builder location(KeyValueSet location) {
            this.location = location;
            return this;
        }

        /**
         * Build the RowDeleter
         * @return RowDeleter
         */
        public RowDeleter build() {
            return new RowDeleter(table, location);
        }
    }

    /**
     * Delete the row, using data from the RowDeleter
     * @return CompletableFuture of whether the operation was successful
     */
    public CompletableFuture<Boolean> execute() {
        return delete(table, location);
    }
}
