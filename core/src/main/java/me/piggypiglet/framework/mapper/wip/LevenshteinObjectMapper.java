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

package me.piggypiglet.framework.mapper.wip;

import me.piggypiglet.framework.mapper.ObjectMapper;
import me.piggypiglet.framework.utils.ReflectionUtils;
import me.piggypiglet.framework.utils.SearchUtils;
import me.piggypiglet.framework.utils.StringUtils;
import me.piggypiglet.framework.utils.number.NumberUtils;
import sun.misc.Unsafe;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Intelligent object mapper that utilises the levenshtein distance algorithm via a port of python's
 * fuzzywuzzy library to map values to fields based on "roughly the same" field names. There's no
 * limit on how similar/unsimilar a name must be for it to be mapped, the mapper will simply pick the
 * closest match.
 */
public abstract class LevenshteinObjectMapper<T> implements ObjectMapper<Map<String, Object>, T> {
    private static final Unsafe UNSAFE;

    static {
        try {
            UNSAFE = (Unsafe) ReflectionUtils.getAccessible(Unsafe.class.getDeclaredField("theUnsafe")).get(null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private final Class<T> clazz;
    private final Constructor<T> constructor;
    private final Map<String, ClassInfo> types;
    private final Map<Class<?>, ObjectMapper<?, ?>> mappers;
    private final Map<String, Field> fields;

    /**
     * Same as LevenshteinObjectMapper(Class&lt;T&gt;) but uses reflection to fetch the class from the generic.
     */
    @SuppressWarnings("unchecked")
    protected LevenshteinObjectMapper() {
        final LevenshteinObjectMapper<T> mapper = new LevenshteinObjectMapper<T>((Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]) {};
        clazz = mapper.clazz;
        constructor = mapper.constructor;
        types = mapper.types;
        mappers = mapper.mappers;
        fields = mapper.fields;
    }

    /**
     * Create an instance of LevenshteinObjectMapper and cache important data. Provide the class reference to save time on initialisation.
     *
     * @param objectClass Class data will be mapped to and from
     */
    @SuppressWarnings("unchecked")
    protected LevenshteinObjectMapper(Class<T> objectClass) {
        clazz = objectClass;

        constructor = (Constructor<T>) Arrays.stream(clazz.getConstructors())
                .filter(c -> c.getParameterCount() == 0)
                .findAny()
                .orElse(null);

        types = Arrays.stream(clazz.getDeclaredFields())
                .collect(Collectors.toMap(
                        Field::getName,
                        f -> new ClassInfo(f.getType().isPrimitive() ? NumberUtils.primitiveToWrapper(f.getType()) : f.getType(), f.getGenericType().getTypeName())
                ));

        mappers = new HashMap<>();
        types.values().forEach(info -> {
            final Class<?> clazz = info.clazz;
            final String typeParams = info.typeParams;

            ObjectMapper<?, ?> mapper = null;

            if (typeParams.contains("<")) {
                final String generic = typeParams.replace(" ", "");
                final List<Type> types;

                try {
                    types = types(generic.substring(generic.indexOf('<'), generic.lastIndexOf('>'))).stream()
                            .flatMap(this::flatten)
                            .collect(Collectors.toList());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }


            }
        });

        fields = types.keySet().stream().collect(Collectors.toMap(s -> s, s -> {
            try {
                return ReflectionUtils.getAccessible(clazz.getDeclaredField(s));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }));
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
        final List<SearchUtils.Searchable> searchables = data.keySet().stream().map(SearchUtils::stringSearchable).collect(Collectors.toList());

        types.forEach((s, t) -> {
            String key;

            if (data.containsKey(s)) {
                key = s;
            } else {
                key = SearchUtils.search(searchables, s).get(0).getName();
            }

            Object o = data.get(key);
            final Class<?> c = t.clazz;

            if (Map.class.isAssignableFrom(c) && !(o instanceof Map)) {
                o = data.entrySet().stream()
                        .filter(e -> e.getKey().startsWith(key))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            } else if (Number.class.isAssignableFrom(c) && !c.isInstance(o)) {
                final Number num = (Number) o;

                switch (c.getSimpleName().toLowerCase()) {
                    case "short":
                        o = num.shortValue();
                        break;

                    case "integer":
                        o = num.intValue();
                        break;

                    case "long":
                        o = num.longValue();
                        break;

                    case "float":
                        o = num.floatValue();
                        break;

                    case "double":
                        o = num.doubleValue();
                        break;
                }
            }

            result.put(s, o);
        });

        final T instance = createInstance();

        result.forEach((s, o) -> {
            try {
                fields.get(s).set(instance, o);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

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
        return fields.values().stream().collect(Collectors.toMap(Field::getName, f -> {
            try {
                return f.get(type);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }));
    }

    @SuppressWarnings("unchecked")
    private T createInstance() {
        try {
            return constructor == null ? (T) UNSAFE.allocateInstance(clazz) : constructor.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private List<Type> types(String input) throws Exception {
        final List<Type> result = new ArrayList<>();
        final String[] types = input.split(",");

        for (String type : types) {
            final String clazz = type.split("<")[0];
            final String params = type.substring(type.indexOf('<'), type.lastIndexOf('>'));
            final List<Type> typeParams;

            if (!clazz.equals(params)) {
                typeParams = types(params);
            } else {
                typeParams = new ArrayList<>();
            }

            result.add(new Type(Class.forName(clazz), typeParams));
        }

        return result;
    }

    private Stream<Type> flatten(Type type) {
        if (type.types.isEmpty()) {
            return Stream.of(type);
        }

        return type.types.stream().flatMap(this::flatten);
    }

    @SuppressWarnings("unchecked")
    private ObjectMapper<?, ?> createMapper(Class<?> clazz) {
        ObjectMapper<?, ?> mapper = null;

        switch (Stream.of(Map.class, Iterable.class, Enum.class).filter(c -> c.isAssignableFrom(clazz))
                .map(Class::getSimpleName)
                .map(String::toLowerCase)
                .findAny().orElse("null")) {
            case "map":
                break;

            case "iterable":
                break;

            case "enum":
                mapper = new EnumObjectMapper((Class<? extends Enum<?>>) clazz);
                break;
        }

        if (!StringUtils.anyWith(clazz,
                Arrays.asList(Map.class, Iterable.class, Enum.class, String.class, Number.class, Boolean.class, Byte.class, Character.class),
                Class::isAssignableFrom)) {
            mapper = new LevenshteinObjectMapper<Object>((Class<Object>) clazz){};
        }

        return mapper;
    }

    private static class ClassInfo {
        private final Class<?> clazz;
        private final String typeParams;

        public ClassInfo(Class<?> clazz, String typeParams) {
            this.clazz = clazz;
            this.typeParams = typeParams;
        }
    }

    private static class Type {
        private final Class<?> clazz;
        private final List<Type> types;

        private Type(Class<?> clazz, List<Type> types) {
            this.clazz = clazz;
            this.types = types;
        }
    }
}