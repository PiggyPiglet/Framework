package me.piggypiglet.framework.managers;

import java.util.ArrayList;
import java.util.List;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public abstract class Manager<S> {
    protected final List<S> items = new ArrayList<>();

    /**
     * Code to populate the provided variable
     * @param items List that needs to be populated
     */
    protected abstract void populate(final List<S> items);

    /**
     * Populate the manager
     */
    public void setup() {
        populate(items);
    }

    /**
     * Add an item to a manager.
     * @param item Item to add
     */
    public void add(S item) {
        items.add(item);
    }

    /**
     * Get all stored items
     * @return List of items
     */
    public List<S> getAll() {
        return new ArrayList<>(items);
    }
}