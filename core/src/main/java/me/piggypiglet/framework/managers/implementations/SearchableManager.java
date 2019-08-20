package me.piggypiglet.framework.managers.implementations;

import me.piggypiglet.framework.managers.Manager;
import me.piggypiglet.framework.utils.SearchUtils;

import java.util.List;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public abstract class SearchableManager<S extends SearchUtils.Searchable> extends Manager<S> {
    /**
     * Search the manager with a query, uses levenshtein weighted ratio.
     * @param query Query string
     * @return List of searchables, in order of most similar to least.
     */
    @SuppressWarnings("unchecked")
    public List<S> search(String query) {
        return SearchUtils.search((List<SearchUtils.Searchable>) items, query);
    }
}
