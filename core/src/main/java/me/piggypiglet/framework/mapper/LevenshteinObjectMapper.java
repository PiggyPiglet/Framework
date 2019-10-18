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

package me.piggypiglet.framework.mapper;

import me.piggypiglet.framework.utils.SearchUtils;
import me.piggypiglet.framework.utils.clazz.ClassUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Intelligent object mapper that utilises the levenshtein distance algorithm via a port of python's
 * fuzzywuzzy library to map values to fields based on "roughly the same" field names. There's no
 * limit on how similar/unsimilar a name must be for it to be mapped, the mapper will simply pick the
 * closest match.
 */
public abstract class LevenshteinObjectMapper<T> implements ObjectMapper<Map<String, Object>, T> {
    private final Constructor<T> constructor;
    private final Object[] params;
    private final Map<String, Class<?>> types = new LinkedHashMap<>();
    private final Map<String, Field> fields = new HashMap<>();

    /**
     * Same as LevenshteinObjectMapper(Class&lt;T&gt;) but uses reflection to fetch the class from the generic.
     */
    @SuppressWarnings("unchecked")
    protected LevenshteinObjectMapper() {
        final LevenshteinObjectMapper<T> mapper = new LevenshteinObjectMapper<T>((Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]){};
        constructor = mapper.constructor;
        params = mapper.params;
        types.putAll(mapper.types);
        fields.putAll(mapper.fields);
    }

    /**
     * Create an instance of LevenshteinObjectMapper and cache important data. Provide the class reference to save time on initialisation.
     * 
     * @param clazz Class data will be mapped to and from
     */
    @SuppressWarnings("unchecked")
    protected LevenshteinObjectMapper(Class<T> clazz) {
        constructor = (Constructor<T>) Arrays.stream(clazz.getConstructors())
                .max(Comparator.comparing(Constructor::getParameterCount))
                .orElseThrow(RuntimeException::new);
        params = Arrays.stream(constructor.getParameterTypes()).map(p -> {
            try {
                return p.isPrimitive() ? ClassUtils.wrapperToPrimitive(p) : null;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).toArray();

        Arrays.stream(clazz.getDeclaredFields()).forEach(
                f -> types.put(f.getName(), f.getType().isPrimitive() ? ClassUtils.primitiveToWrapper(f.getType()) : f.getType())
        );

        types.keySet().forEach(s -> {
            try {
                final Field f = clazz.getDeclaredField(s);
                f.setAccessible(true);
                fields.put(s, f);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * Convert a map (String, Object) into an instance of T. Uses the levenshtein algorithm to find the field names that are the closest to the ones provided.
     *
     * @param data Map of field names (rough) and values
     * @return instance of T
     */
    @Override
    public final T dataToType(Map<String, Object> data) {
        final Map<String, Object> result = new LinkedHashMap<>();
        T instance;

        final List<SearchUtils.Searchable> searchables = data.keySet().stream().map(StringSearchable::new).collect(Collectors.toList());

        types.keySet().forEach(s -> {
            String key;

            if (data.containsKey(s)) {
                key = s;
            } else {
                key = SearchUtils.search(searchables, s).get(0).getName();
            }

            result.put(s, data.get(key));
        });

        final List<Class<?>> required = new ArrayList<>(types.values().stream().map(t -> {
            if (t == Integer.class || t == Long.class) {
                return Double.class;
            }

            return t;
        }).collect(Collectors.toList()));
        
        final List<Class<?>> inputted = new ArrayList<>(result.values().stream().map(Object::getClass).collect(Collectors.toList()));

        boolean canUseConstructor = true;

        for (int i = 0; i < inputted.size(); i++) {
            Class<?> require = required.get(i);
            Class<?> input = inputted.get(i);

            if (!input.equals(require) && !require.isAssignableFrom(input)) {
                canUseConstructor = false;
            }
        }

        if (canUseConstructor) {
            try {
                List<Class<?>> typeValues = new ArrayList<>(types.values());
                List<Object> resultValues = new ArrayList<>(result.values());
                List<Object> values = new ArrayList<>();

                for (int i = 0; i < resultValues.size(); i++) {
                    final Object value = resultValues.get(i);
                    final Class clazz = value.getClass();

                    if (clazz == Double.class) {
                        final Double d = (Double) value;

                        switch (typeValues.get(i).getSimpleName().toLowerCase()) {
                            case "integer":
                                values.add(d.intValue());
                                continue;

                            case "long":
                                values.add(d.longValue());
                                continue;
                        }
                    }

                    values.add(value);
                }

                instance = constructor.newInstance(values.toArray());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            instance = createInstance();

            result.forEach((s, o) -> {
                try {
                    fields.get(s).set(instance, o);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }

        return instance;
    }

    /**
     * Convert an instance of T to a map with KV String, Object, with K representing field names and V their respective values.
     *
     * @param type Value of type T
     * @return Map - Key: String, Value: Object
     */
    @Override
    public final Map<String, Object> typeToData(T type) {
        return fields.values().stream().collect(Collectors.toMap(f -> f.getName(), f -> {
            try {
                return f.get(type);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }));
    }

    private T createInstance() {
        try {
            return constructor.newInstance(params);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static class StringSearchable implements SearchUtils.Searchable {
        private final String str;

        private StringSearchable(String str) {
            this.str = str;
        }

        @Override
        public String getName() {
            return str;
        }
    }
}