package me.piggypiglet.framework.mysql.components.row;

import co.aikar.idb.DbRow;
import me.piggypiglet.framework.mysql.components.MySQLComponent;
import me.piggypiglet.framework.mysql.components.row.objects.Location;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class RowGetter extends MySQLComponent {
    private final String table;
    private final List<Location> locations;

    private RowGetter(String table, List<Location> locations) {
        this.table = table;
        this.locations = locations;
    }

    public static Builder builder(String table) {
        return new Builder(table);
    }

    public static class Builder {
        private final String table;
        private final List<Location> locations = new ArrayList<>();

        private Builder(String table) {
            this.table = table;
        }

        public Builder locations(Location... locations) {
            this.locations.addAll(Arrays.asList(locations));
            return this;
        }

        public RowGetter build() {
            return new RowGetter(table, locations);
        }
    }

    public CompletableFuture<List<DbRow>> getAll() {
        return getAll(table);
    }

    public CompletableFuture<Boolean> exists() {
        return exists(table, locations);
    }
}