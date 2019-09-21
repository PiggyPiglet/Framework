package me.piggypiglet.framework.managers.objects;

import java.util.function.Function;

public final class KeyFunction<T> {
    private final Class<T> type;
    private final Function<T, Object> function;
    private final boolean clazz;

    public KeyFunction(Class<T> type, Function<T, Object> function, boolean clazz) {
        this.type = type;
        this.function = function;
        this.clazz = clazz;
    }

    public Class<T> getType() {
        return type;
    }

    public Function<T, Object> getFunction() {
        return function;
    }

    public boolean isClazz() {
        return clazz;
    }
}
