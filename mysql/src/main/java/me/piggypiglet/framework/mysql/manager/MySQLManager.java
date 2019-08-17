package me.piggypiglet.framework.mysql.manager;

import me.piggypiglet.framework.managers.Manager;
import me.piggypiglet.framework.mysql.table.Table;
import me.piggypiglet.framework.utils.SearchUtils;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public abstract class MySQLManager<S extends SearchUtils.Searchable> extends Manager<S> {
    private final Table<S> table;

    protected MySQLManager(Table<S> table) {
        this.table = table;
    }

    @Override
    public void setup() {
        table.getAll().whenComplete((s, t) -> {
            items.addAll(s);
            populate(items);
        });
    }

    public Table<S> getTable() {
        return table;
    }
}
