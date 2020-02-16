package me.piggypiglet.framework.mapping.gson.builders;

import com.google.gson.Gson;
import me.piggypiglet.framework.mapping.gson.GsonObjectMappers;
import me.piggypiglet.framework.mapping.gson.framework.GsonObjectMapper;
import me.piggypiglet.framework.mapping.gson.objects.GsonObjectMapperData;
import me.piggypiglet.framework.utils.builder.AbstractBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public final class GsonObjectMapperBuilder<T, R> extends AbstractBuilder<GsonObjectMapper<T>, R> {
    private final Function<GsonObjectMapperData, ? extends GsonObjectMapper<T>> initializer;
    private final Class<T> type;

    private Gson gson = GsonObjectMappers.GSON;

    public GsonObjectMapperBuilder(@NotNull final Function<GsonObjectMapperData, ? extends GsonObjectMapper<T>> initializer, @NotNull final Class<T> type) {
        this.initializer = initializer;
        this.type = type;
    }

    public GsonObjectMapperBuilder<T, R> gson(@NotNull final Gson value) {
        gson = value;
        return this;
    }

    @Override
    protected GsonObjectMapper<T> provideBuild() {
        return initializer.apply(new GsonObjectMapperData(type, gson));
    }
}
