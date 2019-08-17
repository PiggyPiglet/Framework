package me.piggypiglet.framework.managers;

import me.piggypiglet.framework.utils.SearchUtils;

import java.util.ArrayList;
import java.util.List;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public abstract class Manager<S extends SearchUtils.Searchable> {
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
     * Search the manager with a query, uses levenshtein weighted ratio.
     * @param query Query string
     * @return List of searchables, in order of most similar to least.
     */
    @SuppressWarnings("unchecked")
    public List<S> search(String query) {
        return SearchUtils.search((List<SearchUtils.Searchable>) items, query);
    }

    /**
     * Get all stored items
     * @return List of items
     */
    public List<S> getAll() {
        return new ArrayList<>(items);
    }
}