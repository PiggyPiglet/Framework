package me.piggypiglet.framework.mysql.components.row.objects;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class Location {
    private final String key;
    private final Object value;

    private Location(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String key;
        private String value;

        public Builder key(String key) {
            this.key = key;
            return this;
        }

        public Builder value(String value) {
            this.value = value;
            return this;
        }

        public Location build() {
            return new Location(key, value);
        }
    }

    public Object getValue() {
        return value;
    }

    public String getKey() {
        return key;
    }
}