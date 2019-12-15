/*
 * MIT License
 *
 * Copyright (c) 2019 PiggyPiglet
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

import java.util.AbstractMap;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Maps {
    private Maps() {
        throw new RuntimeException("This class cannot be instantiated.");
    }

    public static <K, V, R> Builder<K, V, R, V> builder(Map<K, V> implementation, R returnInstance, Consumer<Map<K, V>> build) {
        return builder(implementation, returnInstance, build, null);
    }

    public static <K, V, R, T> Builder<K, V, R, T> builder(Map<K, V> implementation, R returnInstance, Consumer<Map<K, V>> build, Function<T, V> mapper) {
        return new Builder<>(implementation, returnInstance, build, mapper);
    }

    public static <K, V> Builder<K, V, Map<K, V>, V> of(Map<K, V> implementation) {
        return builder(implementation, null, null, null);
    }

    public static <K, V, T> Builder<K, V, Map<K, V>, T> of(Map<K, V> implementation, Function<T, V> mapper) {
        return builder(implementation, null, null, mapper);
    }

    public static final class Builder<K, V, R, T> {
        private final Map<K, V> map;
        private final R returnInstance;
        private final Consumer<Map<K, V>> build;
        private final Function<T, V> mapper;

        private Builder(Map<K, V> implementation, R returnInstance, Consumer<Map<K, V>> build, Function<T, V> mapper) {
            map = implementation;
            this.returnInstance = returnInstance;
            this.build = build;
            this.mapper = mapper;
        }

        public ValueBuilder key(K key) {
            return new ValueBuilder(key);
        }

        public final class ValueBuilder {
            private final K key;

            private ValueBuilder(K key) {
                this.key = key;
            }

            @SuppressWarnings("unchecked")
            public Builder<K, V, R, T> value(T value) {
                if (mapper != null) {
                    map.put(key, mapper.apply(value));
                } else {
                    map.put(key, (V) value);
                }

                return Builder.this;
            }
        }

        @SuppressWarnings("unchecked")
        public R build() {
            if (returnInstance != null && build != null) {
                build.accept(map);
                return returnInstance;
            }

            return (R) map;
        }
    }

    @SuppressWarnings("unchecked")
    public static Stream<Map.Entry<String, Object>> flatten(Map.Entry<String, Object> entry) {
        return flatten(entry, Map.class, f -> f);
    }

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

    @SuppressWarnings("unchecked")
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
            return keys.size() >= 1 && keys.stream().allMatch(s -> s instanceof String);
        }

        return false;
    }
}