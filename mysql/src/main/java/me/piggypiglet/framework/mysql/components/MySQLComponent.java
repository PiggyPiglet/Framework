package me.piggypiglet.framework.mysql.components;

import co.aikar.idb.DbRow;
import com.google.common.collect.Maps;
import me.piggypiglet.framework.mysql.components.row.objects.KeyValueSet;
import me.piggypiglet.framework.mysql.utils.MySQLUtils;

import java.util.List;
import java.util.concurrent.CompletableFuture;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public abstract class MySQLComponent {
    protected CompletableFuture<Boolean> create(String table, KeyValueSet data) {
        return MySQLUtils.create(table, data.getKeys(), data.getValues());
    }

    protected CompletableFuture<DbRow> get(String table, KeyValueSet location) {
        return MySQLUtils.getRow(table, location.getKeys(), location.getValues());
    }

    protected CompletableFuture<List<DbRow>> getAll(String table) {
        return MySQLUtils.getRows(table);
    }

    protected CompletableFuture<Boolean> exists(String table, KeyValueSet location) {
        return MySQLUtils.exists(table, location.getKeys(), location.getValues());
    }

    protected CompletableFuture<Boolean> delete(String table, KeyValueSet location) {
        return MySQLUtils.remove(table, location.getKeys(), location.getValues());
    }

    protected CompletableFuture<Boolean> edit(String table, KeyValueSet location, KeyValueSet changes) {
        return MySQLUtils.set(table, Maps.immutableEntry(location.getKeys(), location.getValues()), Maps.immutableEntry(changes.getKeys(), changes.getValues()));
    }
}
