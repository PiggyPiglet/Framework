package me.piggypiglet.framework.mysql.components.row;

import me.piggypiglet.framework.mysql.components.MySQLComponent;
import me.piggypiglet.framework.mysql.components.row.objects.KeyValueSet;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class RowCreator extends MySQLComponent {
    private final String table;
    private final KeyValueSet data;

    private RowCreator(String table, KeyValueSet data) {
        this.table = table;
        this.data = data;
    }

    public static Builder builder(String table) {
        return new Builder(table);
    }

    public static class Builder {
        private final String table;
        private final KeyValueSet.Builder builder = KeyValueSet.builder();

        private Builder(String table) {
            this.table = table;
        }

        public Builder key(String... keys) {
            Arrays.stream(keys).forEach(builder::key);
            return this;
        }

        public Builder value(Object... values) {
            Arrays.stream(values).forEach(builder::value);
            return this;
        }

        public RowCreator build() {
            return new RowCreator(table, builder.build());
        }
    }

    public CompletableFuture<Boolean> execute() {
        return create(table, data);
    }
}