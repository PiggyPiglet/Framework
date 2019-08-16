package me.piggypiglet.framework.mysql.components.row;

import me.piggypiglet.framework.mysql.components.MySQLComponent;
import me.piggypiglet.framework.mysql.components.row.objects.KeyValueSet;

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

        public Builder location(KeyValueSet location) {
            this.location = location;
            return this;
        }

        public Builder changes(KeyValueSet changes) {
            this.changes = changes;
            return this;
        }

        public RowEditor build() {
            return new RowEditor(table, location, changes);
        }
    }

    public boolean execute() {
        return edit(table, location, changes);
    }
}