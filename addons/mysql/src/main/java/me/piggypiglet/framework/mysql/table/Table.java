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

package me.piggypiglet.framework.mysql.table;

import me.piggypiglet.framework.mapping.ObjectMapper;
import me.piggypiglet.framework.mysql.components.row.RowCreator;
import me.piggypiglet.framework.mysql.components.row.RowDeleter;
import me.piggypiglet.framework.mysql.components.row.RowEditor;
import me.piggypiglet.framework.mysql.components.row.RowGetter;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public abstract class Table<T> implements ObjectMapper<Map<String, Object>, T> {
    private final String table;

    protected Table(String table) {
        this.table = table;
    }

    /**
     * Optionally configure which columns will be checked when using #save. Will use typeToRow by default.
     * @param t Type
     * @return Map of String and Object
     */
    protected Map<String, Object> saveLocation(T t) {
        return typeToData(t);
    }

    /**
     * Get a new instance of RowCreator Builder
     * @return RowCreator Builder
     */
    protected RowCreator.Builder creator() {
        return RowCreator.builder(table);
    }

    /**
     * Get a new instance of RowGetter Builder
     * @return RowGetter Builder
     */
    protected RowGetter.Builder getter() {
        return RowGetter.builder(table);
    }

    /**
     * Get a new instance of RowEditor Builder
     * @return RowEditor Builder
     */
    protected RowEditor.Builder editor() {
        return RowEditor.builder(table);
    }

    /**
     * Get a new instance of RowDeleter Builder
     * @return RowDeleter Builder
     */
    protected RowDeleter.Builder deleter() {
        return RowDeleter.builder(table);
    }

    /**
     * Get all rows in this table
     * @return CompletableFuture of list of table type
     */
    public CompletableFuture<List<T>> getAll() {
        return getter().build().getAll().thenApply(rows -> rows.stream().map(this::dataToType).collect(Collectors.toList()));
    }

    /**
     * Save a type to the table
     * @param t Type
     * @return CompletableFuture of whether the save was successful.
     */
    public CompletableFuture<Boolean> save(T t) {
        final Map<String, Object> location = saveLocation(t);
        final AtomicReference<CompletableFuture<Boolean>> future = new AtomicReference<>();

        getter().location(location).build().exists().whenComplete((b, th) -> {
            final Map<String, Object> row = typeToData(t);

            if (!b) {
                future.set(creator().data(row).build().execute());
            } else {
                getter().location(location).build().get().whenComplete((r, th_) -> {
                   if (row.values().stream().anyMatch(o -> !r.containsValue(o))) {
                       future.set(editor().location(location).changes(row).build().execute());
                   }
                });
            }
        });

        return future.get();
    }

    public CompletableFuture<Boolean> delete(T t) {
        return deleter().location(saveLocation(t)).build().execute();
    }
}