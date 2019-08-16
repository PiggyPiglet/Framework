package me.piggypiglet.framework.mysql.components.row.objects;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class Row {
    private final String[] keys;
    private final Object[] values;

    public Row(String[] keys, Object[] values) {
        this.keys = keys;
        this.values = values;
    }

    public String[] getKeys() {
        return keys;
    }

    public Object[] getValues() {
        return values;
    }
}