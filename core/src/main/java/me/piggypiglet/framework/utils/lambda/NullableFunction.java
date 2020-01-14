package me.piggypiglet.framework.utils.lambda;

// ------------------------------
// Copyright (c) PiggyPiglet 2020
// https://www.piggypiglet.me
// ------------------------------
@FunctionalInterface
public interface NullableFunction<T, R> {
    R execute(T t);

    default R apply(T t) {
        if (t == null) {
            return null;
        }

        return execute(t);
    }
}
