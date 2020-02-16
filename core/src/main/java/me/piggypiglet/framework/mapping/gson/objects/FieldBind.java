package me.piggypiglet.framework.mapping.gson.objects;

import me.piggypiglet.framework.utils.SearchUtils;

import java.lang.reflect.Field;

public final class FieldBind implements SearchUtils.Searchable {
    private final String name;
    private final Field field;

    public FieldBind(String name, Field field) {
        this.name = name;
        this.field = field;
    }

    @Override
    public String getName() {
        return name;
    }

    public Field getField() {
        return field;
    }
}
