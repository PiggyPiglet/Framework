package me.piggypiglet.framework.mapping.gson;

import com.google.gson.Gson;
import me.piggypiglet.framework.mapping.gson.builders.GsonObjectMapperBuilder;
import me.piggypiglet.framework.mapping.gson.framework.GsonObjectMapper;
import me.piggypiglet.framework.mapping.gson.implementations.LevenshteinObjectMapper;
import me.piggypiglet.framework.mapping.gson.objects.GsonObjectMapperData;
import me.piggypiglet.framework.utils.builder.BuilderUtils;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

@SuppressWarnings({"unchecked", "rawtypes"})
public enum GsonObjectMappers {
    LEVENSHTEIN(LevenshteinObjectMapper::new),
    DEFAULT(LEVENSHTEIN.initializer);

    private final Function<GsonObjectMapperData, ? extends GsonObjectMapper<?>> initializer;

    GsonObjectMappers(@NotNull final Function<GsonObjectMapperData, ? extends GsonObjectMapper<?>> initializer) {
        this.initializer = initializer;
    }

    public static final Gson GSON = new Gson();

    public <T> GsonObjectMapper<T> create(@NotNull final Class<T> type) {
        return (GsonObjectMapper<T>) initializer.apply(new GsonObjectMapperData(type, GSON));
    }

    public <T> GsonObjectMapperBuilder<T, GsonObjectMapper<T>> createBuilder(@NotNull final Class<T> type) {
        return builder((Function) initializer, type);
    }

    public <T, R> GsonObjectMapperBuilder<T, R> createBuilder(@NotNull final Class<T> type, @NotNull final Function<GsonObjectMapper<T>, R> builder) {
        return builder((Function) initializer, type, builder);
    }

    public static <T> GsonObjectMapper<T> of(@NotNull final Class<T> type) {
        return DEFAULT.create(type);
    }

    public static <T> GsonObjectMapper<T> of(@NotNull final Function<GsonObjectMapperData, ? extends GsonObjectMapper<T>> initializer,
                                             @NotNull final Class<T> type) {
        return builder(initializer, type).build();
    }

    public static <T> GsonObjectMapperBuilder<T, GsonObjectMapper<T>> builder(@NotNull final Class<T> type) {
        return builder((Function) DEFAULT.initializer, type);
    }

    public static <T, R> GsonObjectMapperBuilder<T, R> builder(@NotNull final Class<T> type, @NotNull final Function<GsonObjectMapper<T>, R> builder) {
        return builder((Function) DEFAULT.initializer, type, builder);
    }

    public static <T> GsonObjectMapperBuilder<T, GsonObjectMapper<T>> builder(@NotNull final Function<GsonObjectMapperData, ? extends GsonObjectMapper<T>> initializer,
                                                                               @NotNull final Class<T> type) {
        return new GsonObjectMapperBuilder<>(initializer, type);
    }

    public static <T, R> GsonObjectMapperBuilder<T, R> builder(@NotNull final Function<GsonObjectMapperData, ? extends GsonObjectMapper<T>> initializer,
                                                               @NotNull final Class<T> type, @NotNull final Function<GsonObjectMapper<T>, R> builder) {
        return BuilderUtils.customBuilder(new GsonObjectMapperBuilder<>(initializer, type), builder);
    }
}
