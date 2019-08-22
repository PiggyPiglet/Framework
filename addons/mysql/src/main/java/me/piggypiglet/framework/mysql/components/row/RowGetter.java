package me.piggypiglet.framework.mysql.components.row;

import co.aikar.idb.DbRow;
import me.piggypiglet.framework.mysql.components.MySQLComponent;
import me.piggypiglet.framework.mysql.components.row.objects.KeyValueSet;

import java.util.List;
import java.util.concurrent.CompletableFuture;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class RowGetter extends MySQLComponent {
    private final String table;
    private final KeyValueSet location;

    private RowGetter(String table, KeyValueSet location) {
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
         * Location of the row(s) to find
         * @param location Location
         * @return Builder instance
         */
        public Builder location(KeyValueSet location) {
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