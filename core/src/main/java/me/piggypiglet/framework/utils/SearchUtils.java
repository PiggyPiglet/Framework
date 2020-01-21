/*
 * MIT License
 *
 * Copyright (c) 2019-2020 PiggyPiglet
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package me.piggypiglet.framework.utils;

import com.google.common.collect.ImmutableList;
import me.xdrop.fuzzywuzzy.FuzzySearch;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class SearchUtils {
    private SearchUtils() {
        throw new RuntimeException("This class cannot be instantiated.");
    }

    /**
     * Use the levenshtein algorithm, via a java fuzzy wuzzy port (python library), to order a list of items based on string similarity.
     * Will leverage multithreading if items size exceeds 5000.
     *
     * @param items Items to re order
     * @param query Search query
     * @param <T>   Searchable types
     * @return ImmutableList of re-ordered searchables
     */
    @SuppressWarnings("unchecked")
    public static <T extends Searchable> ImmutableList<T> search(List<Searchable> items, String query) {
        List<SearchPair> pairs = items.stream().map(i -> new SearchPair(i, query)).sorted().collect(Collectors.toList());
        Collections.reverse(pairs);

        Stream<SearchPair> stream = pairs.stream();

        if (pairs.size() > 5000) {
            stream = stream.parallel();
        }

        return stream
                .map(i -> (T) i.item)
                .collect(Collectors.collectingAndThen(Collectors.toList(), ImmutableList::copyOf));
    }

    public static Searchable stringSearchable(String str) {
        return () -> str;
    }

    private static final class SearchPair implements Comparable {
        private final Searchable item;
        private final String query;
        private int similarity;

        private SearchPair(Searchable item, String query) {
            this.item = item;
            this.query = query;
            this.similarity = FuzzySearch.weightedRatio(item.getName(), query);
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
        /**
         * The string used when a searchable is compared to other searchables in terms of string similarity
         *
         * @return String
         */
        String getName();
    }
}