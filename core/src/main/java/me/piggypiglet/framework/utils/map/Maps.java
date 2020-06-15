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

package me.piggypiglet.framework.utils.map;

import me.piggypiglet.framework.utils.builder.AbstractBuilder;
import me.piggypiglet.framework.utils.builder.BuilderUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SuppressWarnings("unchecked")
public class Maps {
    private Maps() {
        throw new RuntimeException("This class cannot be instantiated.");
    }

    public static <K, V/*, T*/> Builder<K, V, /*T, */Map<K, V>> of(@NotNull final Map<K, V> implementation) {
        return new Builder<>(implementation);
//        return of(implementation, obj -> (V) obj);
    }

//    public static <K, V, T> Builder<K, V, T, Map<K, V>> of(@NotNull final Map<K, V> implementation, @Nullable final Function<T, V> mapper) {
//        return new Builder<>(implementation, mapper);
//    }

    public static <K, V, /*T, */R> Builder<K, V, /*T, */R> builder(@NotNull final Map<K, V> implementation, @NotNull final Function<Map<K, V>, R> builder) {
        return BuilderUtils.customBuilder(new Builder<>(implementation), builder);
//        return builder(implementation, obj -> (V) obj, builder);
    }

//    public static <K, V, T, R> Builder<K, V, T, R> builder(@NotNull final Map<K, V> implementation, @Nullable final Function<T, V> mapper,
//                                                           @NotNull final Function<Map<K, V>, R> builder) {
//        return BuilderUtils.customBuilder(new Builder<>(implementation, mapper), builder);
//    }

    public static final class Builder<K, V, /*T, */R> extends AbstractBuilder<Map<K, V>, R> {
        private final Map<K, V> map;
//        private final Function<T, V> mapper;

        private Builder(Map<K, V> implementation/*, Function<T, V> mapper*/) {
            map = implementation;
//            this.mapper = mapper;
        }

        /**
         * Prepare a value builder.
         *
         * @param key Key
         * @return Value Builder
         */
        public ValueBuilder key(K key) {
            return key(key, null);
        }

        /**
         * Prepare a value builder with a requirement for addition.
         *
         * @param key         Key
         * @param requirement Requirement for value to be added
         * @return Value Builder
         */
        public ValueBuilder key(K key, Predicate<V> requirement) {
            return new ValueBuilder(key, requirement);
        }

        public final class ValueBuilder {
            private final K key;
            private final Predicate<V> requirement;

            private ValueBuilder(K key, Predicate<V> requirement) {
                this.key = key;
                this.requirement = requirement;
            }

            /**
             * Bind a value to the defined key. If a requirement is present, the value will be tested
             * against it after mapping, before being added to the underlying map implementation.
             *
             * @param value Value
             * @return Parent Map Builder
             */
            public Builder<K, V, /*T, */R> value(/*T*/V value) {
//                final V real = mapper == null ? (V) value : mapper.apply(value);

                if (requirement != null) {
                    if (!requirement.test(value)) return Builder.this;
                }

                map.put(key, value);

                return Builder.this;
            }
        }

        @Override
        protected Map<K, V> provideBuild() {
            return map;
        }
    }

    /**
     * Stream flatMap utility to flatten a map, with "." as the level separator.
     *
     * @param entry Entry to flatten
     * @return Stream of flattened entr(y/ies)
     */
    public static Stream<Map.Entry<String, Object>> flatten(Map.Entry<String, Object> entry) {
        return flatten(entry, Map.class, f -> f);
    }

    /**
     * Stream flatMap utility to flatten a map, with "." as the level separator,
     * and a object mapper (triggered if a configured type is detected within a
     * nested map).
     *
     * @param entry   Entry to flatten
     * @param clazz   Class type of object to detect
     * @param flatten Flatten logic for custom object type
     * @param <T>     Type generic of object
     * @return Stream of flattened entr(y/ies)
     */
    @SuppressWarnings("unchecked")
    public static <T> Stream<Map.Entry<String, Object>> flatten(Map.Entry<String, Object> entry, Class<T> clazz, Function<T, Map<String, Object>> flatten) {
        if (clazz.isInstance(entry.getValue())) {
            return flatten.apply((T) entry.getValue()).entrySet().stream()
                    .map(e -> new AbstractMap.SimpleEntry<>(entry.getKey() + "." + e.getKey(), e.getValue()))
                    .flatMap(e -> flatten(e, clazz, flatten));
        }

        if (entry.getValue() instanceof Collection<?> && ((Collection<?>) entry.getValue()).stream().anyMatch(clazz::isInstance)) {
            return Stream.of(new AbstractMap.SimpleEntry<>(entry.getKey(), ((Collection<?>) entry.getValue()).stream()
                    .map(o -> {
                        if (clazz.isInstance(o)) {
                            return flatten.apply((T) o).entrySet().stream()
                                    .flatMap(e -> flatten(new AbstractMap.SimpleEntry<>(entry.getKey() + "." + e.getKey(), e.getValue()), clazz, flatten))
                                    .distinct()
                                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
                        }

                        return entry;
                    })
                    .collect(Collectors.toList())));
        }

        return Stream.of(entry);
    }

    /**
     * Wrapper around flatten stream util.
     *
     * @param map Map to flatten
     * @return Flattened map
     */
    @NotNull
    public static Map<String, Object> flatten(@NotNull final Map<String, Object> map) {
        return map.entrySet().stream().flatMap(Maps::flatten).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    /**
     * Utility method to sort through a map (including nested maps and collections), and replace
     * a specific type, with a Map.
     *
     * @param map    Map to map
     * @param clazz  Class type of object to detect
     * @param mapper Logic to convert object to map
     * @param <T>    Type generic of object
     * @return Mapped map
     */
    public static <T> Map<String, Object> map(Map<String, Object> map, Class<T> clazz, Function<T, Map<String, Object>> mapper) {
        final Map<String, Object> mapped = new HashMap<>();

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            final Object obj = entry.getValue();

            if (clazz.isInstance(obj)) {
                mapped.put(entry.getKey(), Maps.map(mapper.apply((T) obj), clazz, mapper));
                continue;
            }

            if (entry.getValue() instanceof Collection<?> && ((Collection<?>) entry.getValue()).stream().anyMatch(clazz::isInstance)) {
                mapped.put(entry.getKey(), ((Collection<?>) entry.getValue()).stream()
                        .map(o -> {
                            if (clazz.isInstance(o)) {
                                return Maps.map(mapper.apply((T) o), clazz, mapper);
                            }

                            return o;
                        })
                );
                continue;
            }

            mapped.put(entry.getKey(), obj);
        }

        return mapped;
    }

    /**
     * Utility method to recurse through levels of nested maps to retrieve a specific object,
     * at a path (nested maps separated via "." in the path).
     *
     * @param map  Map to search through
     * @param path Path of object
     * @param <T>  Type generic of object
     * @return Object located at path
     */
    public static <T> T recursiveGet(Map<String, T> map, String path) {
        T object = map.getOrDefault(path, null);

        if (path.contains(".") && !path.startsWith(".") && !path.endsWith(".")) {
            String[] areas = path.split("\\.");
            object = map.getOrDefault(areas[0], null);

            if (areas.length >= 2 && object != null) {
                object = getBuriedObject(map, areas);
            }
        }

        return object;
    }

    private static <T> T getBuriedObject(Map<String, T> map, String[] keys) {
        int i = 1;
        Map<String, T> endObject = (Map<String, T>) map.get(keys[0]);

        while (instanceOfStringKeyMap(endObject.get(keys[i]))) {
            endObject = (Map<String, T>) endObject.get(keys[i++]);
        }

        return endObject.get(keys[i]);
    }

    private static boolean instanceOfStringKeyMap(Object obj) {
        if (obj instanceof Map) {
            final Set<?> keys = ((Map<?, ?>) obj).keySet();
            return !keys.isEmpty() && keys.stream().allMatch(s -> s instanceof String);
        }

        return false;
    }
}