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

    protected abstract T rowToType(DbRow row);

    protected abstract KeyValueSet typeToRow(T t);

    protected abstract KeyValueSet typeToLocation(T t);

    protected RowCreator.Builder creator() {
        return RowCreator.builder(table);
    }

    protected RowGetter.Builder getter() {
        return RowGetter.builder(table);
    }

    protected RowEditor.Builder editor() {
        return RowEditor.builder(table);
    }

    protected RowDeleter.Builder deleter() {
        return RowDeleter.builder(table);
    }

    public CompletableFuture<List<T>> getAll() {
        return getter().build().getAll().thenApply(rows -> rows.stream().map(this::rowToType).collect(Collectors.toList()));
    }

    public CompletableFuture<Boolean> save(T t) {
        final KeyValueSet row = typeToRow(t);
        return creator().key(row.getKeys()).value(row.getValues()).build().execute();
    }
}