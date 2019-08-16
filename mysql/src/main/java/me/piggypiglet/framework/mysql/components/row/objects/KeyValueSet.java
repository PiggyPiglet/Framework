package me.piggypiglet.framework.mysql.components.row.objects;

import java.util.ArrayList;
import java.util.List;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class KeyValueSet {
    private final List<String> keys;
    private final List<Object> values;

    private KeyValueSet(List<String> keys, List<Object> values) {
        this.keys = keys;
        this.values = values;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final List<String> keys = new ArrayList<>();
        private final List<Object> values = new ArrayList<>();

        public Builder key(String key) {
            keys.add(key);
            return this;
        }

        public Builder value(Object value) {
            values.add(value);
            return this;
        }

        public KeyValueSet build() {
            return new KeyValueSet(keys, values);
        }
    }

    public String[] getKeys() {
        return keys.toArray(new String[]{});
    }

    public Object[] getValues() {
        return values.toArray();
    }
}