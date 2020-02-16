package me.piggypiglet.framework.mapping.gson.framework;

import com.google.common.util.concurrent.AtomicDoubleArray;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import me.piggypiglet.framework.mapping.ObjectMapper;
import me.piggypiglet.framework.mapping.gson.objects.FieldBind;
import me.piggypiglet.framework.mapping.gson.objects.GsonObjectMapperData;
import me.piggypiglet.framework.utils.StringUtils;
import me.piggypiglet.framework.utils.primitive.Primitives;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Modifier;
import java.net.InetAddress;
import java.net.URI;
import java.net.URL;
import java.security.Timestamp;
import java.sql.Time;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicLongArray;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class GsonObjectMapper<T> implements ObjectMapper<Map<String, Object>, T> {
    private final Class<T> type;
    private final AtomicReference<Gson> gson = new AtomicReference<>();
    private final Function<GsonObjectMapperData, ? extends GsonObjectMapper<?>> initializer;

    private FieldBinder binder = null;

    @SuppressWarnings("unchecked")
    protected GsonObjectMapper(@NotNull final Function<GsonObjectMapperData, ? extends GsonObjectMapper<?>> initializer, @NotNull final GsonObjectMapperData data) {
        this(initializer, (Class<T>) data.getType(), data.getGson());
    }

    protected GsonObjectMapper(@NotNull final Function<GsonObjectMapperData, ? extends GsonObjectMapper<?>> initializer, @NotNull final Class<T> type,
                               @NotNull final Gson gson) {
        this.type = type;
        this.gson.set(gson);
        this.initializer = initializer;
    }

    public void init() {
        binder = provideBinder(gson.get(), TypeToken.get(type));
    }

    //todo: I don't like this method
    @NotNull
    protected abstract <R> FieldBinder provideBinder(@NotNull final Gson gson, @NotNull final TypeToken<R> type);

    @NotNull
    @Override
    public T dataToType(@NotNull final Map<String, Object> data) {
        final Map<String, Object> filteredData = data.entrySet().stream()
                .map(this::processDataEntry)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        final Gson gson = this.gson.get();
        return gson.fromJson(gson.toJsonTree(filteredData), type);
    }

    @NotNull
    @Override
    public Map<String, Object> typeToData(@NotNull final T type) {
        final Gson gson = this.gson.get();
        return gson.fromJson(gson.toJsonTree(type), new TypeToken<Map<String, Object>>(){}.getType());
    }

    @SuppressWarnings("unchecked")
    private Map.Entry<String, Object> processDataEntry(Map.Entry<String, Object> entry) {
        binderAssertion();
        final FieldBind bind = binder.get(entry.getKey());
        final String key = bind.getName();
        final Object value = entry.getValue();
        final Class<?> clazz = value.getClass();
        final Gson gson = this.gson.get();
        final Class<?> bindType = bind.getField().getType();

        if (value instanceof Map<?, ?> && !Map.class.isAssignableFrom(bindType)) {
            final FieldBinder binder = provideBinder(gson, TypeToken.get(bindType));

            final Map<String, Object> map = ((Map<String, Object>) value).entrySet().stream()
                    .map(e -> new AbstractMap.SimpleEntry<>(binder.get(e.getKey()).getName(), e.getValue()))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

            return new AbstractMap.SimpleEntry<>(key, gson.fromJson(gson.toJsonTree(map), bindType));
        } else if (isConstructable(clazz)) {
            final GsonObjectMapper<?> mapper = initializer.apply(new GsonObjectMapperData(clazz, gson));
            mapper.init();
            final Object data = mapper.typeToData(gson.fromJson(gson.toJsonTree(value), new TypeToken<Map<String, Object>>(){}.getType()));

            return new AbstractMap.SimpleEntry<>(key, data);
        }

        return new AbstractMap.SimpleEntry<>(key, value);
    }

    private void binderAssertion() {
        if (binder == null) {
            throw new AssertionError("Init method hasn't been called on this GsonObjectMapper.");
        }
    }

    private static final List<Class<?>> EXCLUSIONS = Arrays.asList(String.class, Map.class, Iterable.class, Class.class, Number.class,
            AtomicBoolean.class, AtomicIntegerArray.class, AtomicDoubleArray.class, AtomicLongArray.class, StringBuilder.class,
            StringBuffer.class, URL.class, URI.class, UUID.class, Currency.class, Locale.class, InetAddress.class,
            BitSet.class, Date.class, Calendar.class, Time.class, java.sql.Date.class, Timestamp.class, Enum.class, JsonElement.class);

    private static boolean isConstructable(@NotNull final Class<?> clazz) {
        return !clazz.isPrimitive() && Primitives.fromClass(clazz) == Primitives.UNKNOWN &&
                !clazz.isInterface() && !Modifier.isAbstract(clazz.getModifiers()) &&
                clazz != Object.class && !StringUtils.anyWith(clazz, EXCLUSIONS, Class::isAssignableFrom);
    }
}