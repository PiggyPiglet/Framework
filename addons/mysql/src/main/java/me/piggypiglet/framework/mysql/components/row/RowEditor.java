package me.piggypiglet.framework.mysql.components.row;

import me.piggypiglet.framework.mysql.components.MySQLComponent;
import me.piggypiglet.framework.mysql.components.row.objects.KeyValueSet;

import java.util.concurrent.CompletableFuture;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class RowEditor extends MySQLComponent {
    private final String table;
    private final KeyValueSet location;
    private final KeyValueSet changes;

    private RowEditor(String table, KeyValueSet location, KeyValueSet changes) {
        this.table = table;
        this.location = location;
        this.changes = changes;
    }

    public static Builder builder(String table) {
        return new Builder(table);
    }

    public static class Builder {
        private final String table;
        private KeyValueSet location;
        private KeyValueSet changes;

        private Builder(String table) {
            this.table = table;
        }

        /**
         * Location of the row
         * @param location Location
         * @return Builder instance
         */
        public Builder location(KeyValueSet location) {
            this.location = location;
            return this;
        }

        /**
         * Changes to make
         * @param changes Changes
         * @return Builder instance
         */
        public Builder changes(KeyValueSet changes) {
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