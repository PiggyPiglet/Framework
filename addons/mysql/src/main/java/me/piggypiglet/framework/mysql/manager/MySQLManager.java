package me.piggypiglet.framework.mysql.manager;

import me.piggypiglet.framework.managers.implementations.SearchableManager;
import me.piggypiglet.framework.mysql.table.Table;
import me.piggypiglet.framework.mysql.utils.MySQLUtils;
import me.piggypiglet.framework.utils.SearchUtils;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public abstract class MySQLManager<S extends SearchUtils.Searchable> extends SearchableManager<S> {
    private final Table<S> table;

    protected MySQLManager(Table<S> table) {
        this.table = table;
    }

    @Override
    public void setup() {
        MySQLUtils.isReady().whenComplete((b, t) -> {
            if (b) {
                table.getAll().whenComplete((s, th) -> {
                    items.addAll(s);
                    populate(items);
                });
            }
        });
    }

    public Table<S> getTable() {
        return table;
    }
}
