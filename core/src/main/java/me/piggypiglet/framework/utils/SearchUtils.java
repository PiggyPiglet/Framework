package me.piggypiglet.framework.utils;

import com.google.common.collect.ImmutableList;
import me.xdrop.fuzzywuzzy.FuzzySearch;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class SearchUtils {
    @SuppressWarnings("unchecked")
    public static <T extends Searchable> ImmutableList<T> search(List<Searchable> items, String query) {
        List<SearchPair> pairs = items.stream().map(i -> new SearchPair(i, query)).sorted().collect(Collectors.toList());
        Collections.reverse(pairs);

        return pairs.stream()
                .map(i -> (T) i.item)
                .collect(Collectors.collectingAndThen(Collectors.toList(), ImmutableList::copyOf));
    }

    private static final class SearchPair implements Comparable {
        private final Searchable item;
        private final String query;
        private int similarity;

        private SearchPair(Searchable item, String query) {
            this.item = item;
            this.query = query;
            this.similarity = FuzzySearch.weightedRatio(item.getTitle(), query);
        }

        @Override
        public int compareTo(@Nonnull Object o) {
            if (!(o instanceof SearchPair)) return 0;

            return similarity - ((SearchPair) o).similarity;
        }

        @Override
        public String toString() {
            return String.format("SearchPair(item=%s, query=%s, similarity=%s)", item, query, similarity);
        }
    }

    public interface Searchable {
        String getTitle();
    }
}