package me.piggypiglet.framework.mysql.components.row;

import me.piggypiglet.framework.mysql.components.MySQLComponent;
import me.piggypiglet.framework.mysql.components.row.objects.KeyValueSet;

import java.util.concurrent.CompletableFuture;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
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
