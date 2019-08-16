package me.piggypiglet.framework.mysql.components.row;

import me.piggypiglet.framework.mysql.components.row.objects.Location;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class RowDeleter {
    private final String table;
    private final List<Location> locations;

    private RowDeleter(String table, List<Location> locations) {
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

        public RowDeleter build() {
            return new RowDeleter(table, locations);
        }
    }

    public void delete() {

    }
}
