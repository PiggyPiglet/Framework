package me.piggypiglet.framework.mysql.components.row;

import me.piggypiglet.framework.mysql.components.MySQLComponent;
import me.piggypiglet.framework.mysql.components.row.objects.Row;

import java.util.concurrent.CompletableFuture;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class RowCreator extends MySQLComponent {
    private final String table;
    private final String[] keys;
    private final Object[] values;

    private RowCreator(String table, String[] keys, Object[] values) {
        this.table = table;
        this.keys = keys;
        this.values = values;
    }

    public static Builder builder(String table) {
        return new Builder(table);
    }

    public static class Builder {
        private final String table;
        private String[] keys;
        private Object[] values;

        private Builder(String table) {
            this.table = table;
        }

        public Builder keys(String... keys) {
            this.keys = keys;
            return this;
        }

        public Builder values(String... values) {
            this.values = values;
            return this;
        }

        public RowCreator build() {
            return new RowCreator(table, keys, values);
        }
    }

    public CompletableFuture<Boolean> execute() {
        return create(table, new Row(keys, values));
    }
}