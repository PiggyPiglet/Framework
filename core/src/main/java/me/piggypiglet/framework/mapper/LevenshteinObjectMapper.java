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

public abstract class LevenshteinObjectMapper<T> implements ObjectMapper<Map<String, Object>, T> {
    private final Constructor<T> constructor;
    private final Object[] params;

    @SuppressWarnings("unchecked")
    protected LevenshteinObjectMapper() {
        constructor = (Constructor<T>) Arrays.stream(((Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]).getConstructors())
                .max(Comparator.comparing(Constructor::getParameterCount))
                .orElseThrow(RuntimeException::new);
        params = Arrays.stream(constructor.getParameterTypes()).map(p -> {
            try {
                return p.isPrimitive() ? ClassUtils.wrapperToPrimitive(p) : null;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).toArray();
    }

    @Override
    public final T dataToType(Map<String, Object> data) {
        final Map<String, Class<?>> types = new LinkedHashMap<>();
        final Map<String, Object> result = new LinkedHashMap<>();
        T instance;

        Arrays.stream(constructor.getDeclaringClass().getDeclaredFields()).forEach(
                f -> types.put(f.getName(), f.getType().isPrimitive() ? ClassUtils.primitiveToWrapper(f.getType()) : f.getType())
        );

        final List<SearchUtils.Searchable> searchables = types.keySet().stream().map(StringSearchable::new).collect(Collectors.toList());

        data.forEach((s, o) -> {
            String key;

            if (types.containsKey(s)) {
                key = s;
            } else {
                key = SearchUtils.search(searchables, s).get(0).getName();
            }

            result.put(key, o);
        });

        if (new ArrayList<>(types.values()).equals(new ArrayList<>(result.values().stream().map(Object::getClass).collect(Collectors.toList())))) {
            try {
                instance = constructor.newInstance(result.values().toArray());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            instance = createInstance();
            final Class<T> clazz = constructor.getDeclaringClass();

            result.forEach((s, o) -> {
                try {
                    final Field field = clazz.getDeclaredField(s);
                    field.setAccessible(true);
                    field.set(instance, o);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }

        return instance;
    }

    @Override
    public final Map<String, Object> typeToData(T type) {
        return null;
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
