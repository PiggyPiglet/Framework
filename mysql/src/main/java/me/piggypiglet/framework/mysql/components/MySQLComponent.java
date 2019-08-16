package me.piggypiglet.framework.mysql.components;

import co.aikar.idb.DbRow;
import me.piggypiglet.framework.mysql.components.row.objects.Location;
import me.piggypiglet.framework.mysql.components.row.objects.Row;
import me.piggypiglet.framework.mysql.utils.MySQLUtils;

import java.util.List;
import java.util.concurrent.CompletableFuture;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public abstract class MySQLComponent {
    protected CompletableFuture<Boolean> create(String table, Row row) {
        return MySQLUtils.create(table, row.getKeys(), row.getValues());
    }

    protected CompletableFuture<List<DbRow>> getAll(String table) {
        return MySQLUtils.getRows(table);
    }

    protected CompletableFuture<Boolean> exists(String table, List<Location> locations) {
        String[] keys = locations.stream().map(Location::getKey).toArray(String[]::new);
        Object[] values = locations.stream().map(Location::getValue).toArray();

        return MySQLUtils.exists(table, keys, values);
    }
}
