package me.piggypiglet.framework.mysql.table;

import co.aikar.idb.DbRow;
import me.piggypiglet.framework.mysql.components.row.RowCreator;
import me.piggypiglet.framework.mysql.components.row.RowDeleter;
import me.piggypiglet.framework.mysql.components.row.RowEditor;
import me.piggypiglet.framework.mysql.components.row.RowGetter;
import me.piggypiglet.framework.mysql.components.row.objects.KeyValueSet;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public abstract class Table<T> {
    private final String table;

    protected Table(String table) {
        this.table = table;
    }

    /**
     * Convert a row to the table's type
     * @param row Row to convert
     * @return T
     */
    protected abstract T rowToType(DbRow row);

    /**
     * Convert a KeyValueSet to the table's type
     * @param t Type
     * @return KeyValueSet
     */
    protected abstract KeyValueSet typeToRow(T t);

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
        return getter().build().getAll().thenApply(rows -> rows.stream().map(this::rowToType).collect(Collectors.toList()));
    }

    /**
     * Save a type to the table
     * @param t Type
     * @return CompletableFuture of whether the save was successful.
     */
    public CompletableFuture<Boolean> save(T t) {
        final KeyValueSet row = typeToRow(t);
        final CompletableFuture<Boolean> future = new CompletableFuture<>();

        getter().location(row).build().exists().whenComplete((b, th) -> {
            if (b) {
                creator().key(row.getKeys()).value(row.getValues()).build().execute().whenComplete((b_, th_) -> future.complete(b_));
            }
        });

        return future;
    }
}