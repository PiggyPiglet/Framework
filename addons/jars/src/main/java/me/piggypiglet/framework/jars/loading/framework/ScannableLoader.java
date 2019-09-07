package me.piggypiglet.framework.jars.loading.framework;

import me.piggypiglet.framework.utils.annotations.reflection.Disabled;

import java.util.function.Predicate;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
@Disabled
public abstract class ScannableLoader<T> extends Loader {
    private final Predicate<Class<?>> match;

    protected ScannableLoader(String dir, Predicate<Class<?>> match) {
        super(dir);
        this.match = match;
    }

    protected abstract void init(T instance);

    @SuppressWarnings("unchecked")
    public void run(Object instance) {
        init((T) instance);
    }

    public Predicate<Class<?>> getMatch() {
        return match;
    }
}
