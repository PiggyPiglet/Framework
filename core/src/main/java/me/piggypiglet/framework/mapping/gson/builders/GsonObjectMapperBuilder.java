package me.piggypiglet.framework.mapping.gson.builders;

import com.google.gson.Gson;
import me.piggypiglet.framework.mapping.gson.GsonObjectMappers;
import me.piggypiglet.framework.mapping.gson.framework.GsonObjectMapper;
import me.piggypiglet.framework.mapping.gson.objects.GsonObjectMapperData;
import me.piggypiglet.framework.utils.builder.AbstractBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

/**
 * Util builder class for any type of gson object mapper.
 *
 * @param <T> GsonObjectMapper type
 * @param <R> Return type (returned on #build)
 */
public final class GsonObjectMapperBuilder<T, R> extends AbstractBuilder<GsonObjectMapper<T>, R> {
    private final Function<GsonObjectMapperData, GsonObjectMapper<T>> initializer;
    private final Class<T> type;

    private Gson gson = GsonObjectMappers.GSON;

    /**
     * Construct a GsonObjectMapperBuilder instance.
     * Plain POJO-esque constructor.
     *
     * @param initializer GsonObjectMapper type constructor. Invoked
     *                    in #build, to construct the mapper from the
     *                    compiled GsonObjectMapperData.
     * @param type Type the mapper will be responsible for serializing,
     *             and deserializing.
     */
    public GsonObjectMapperBuilder(@NotNull final Function<GsonObjectMapperData, GsonObjectMapper<T>> initializer, @NotNull final Class<T> type) {
        this.initializer = initializer;
        this.type = type;
    }

    /**
     * Provide a custom gson instance. This option is only recommended if
     * the type of mapper you're using actually benefits from custom
     * options. For example, there's no point in setting a field naming
     * strategy if you're using a LevenshteinObjectMapper. By default,
     * GsonObjectMappers.GSON will be used.
     *
     * @param value Gson instance
     * @return GsonObjectMapperBuilder&lt;T, R&gt;
     */
    public GsonObjectMapperBuilder<T, R> gson(@NotNull final Gson value) {
        gson = value;
        return this;
    }

    /**
     * Construct the GsonObjectMapperData with the provided required,
     * and optional fields, then provide it to the initializer to construct
     * the final GsonObjectMapper.
     *
     * @return GsonObjectMapper&lt;T&gt;
     */
    @Override
    protected GsonObjectMapper<T> provideBuild() {
        return initializer.apply(new GsonObjectMapperData(type, gson));
    }
}
