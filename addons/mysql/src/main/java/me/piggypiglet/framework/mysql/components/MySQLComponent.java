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

package me.piggypiglet.framework.mysql.components;

import co.aikar.idb.DbRow;
import me.piggypiglet.framework.mysql.utils.MySQLUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public abstract class MySQLComponent {
    protected CompletableFuture<Boolean> create(String table, Map<String, Object> data) {
        return MySQLUtils.create(table, data);
    }

    protected CompletableFuture<DbRow> get(String table, Map<String, Object> location) {
        return MySQLUtils.getRow(table, location);
    }

    protected CompletableFuture<List<DbRow>> getAll(String table) {
        return MySQLUtils.getRows(table);
    }

    protected CompletableFuture<Boolean> exists(String table, Map<String, Object> location) {
        return MySQLUtils.exists(table, location);
    }

    protected CompletableFuture<Boolean> delete(String table, Map<String, Object> location) {
        return MySQLUtils.remove(table, location);
    }

    protected CompletableFuture<Boolean> edit(String table, Map<String, Object> location, Map<String, Object> changes) {
        return MySQLUtils.set(table, location, changes);
    }
}