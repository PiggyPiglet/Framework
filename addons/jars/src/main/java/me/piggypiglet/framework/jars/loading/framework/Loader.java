package me.piggypiglet.framework.jars.loading.framework;

import java.io.File;
import java.util.function.Predicate;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public abstract class Loader<T> {
    private final String dir;
    private final Predicate<Class<?>> match;

    protected Loader(String dir, final Predicate<Class<?>> match) {
        this.dir = dir;
        this.match = match;
    }

    public abstract Jar[] process(File[] files);

    protected abstract void init(T instance);

    @SuppressWarnings("unchecked")
    public void run(Object instance) {
        init((T) instance);
    }

    public String getDir() {
        return dir;
    }

    public Predicate<Class<?>> getMatch() {
        return match;
    }
}
