package me.piggypiglet.framework.mapping.gson.implementations;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import me.piggypiglet.framework.mapping.gson.framework.FieldBinder;
import me.piggypiglet.framework.mapping.gson.framework.GsonObjectMapper;
import me.piggypiglet.framework.mapping.gson.objects.FieldBind;
import me.piggypiglet.framework.mapping.gson.objects.GsonObjectMapperData;
import me.piggypiglet.framework.utils.SearchUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public final class LevenshteinObjectMapper<T> extends GsonObjectMapper<T> {
    public LevenshteinObjectMapper(@NotNull final GsonObjectMapperData data) {
        super(LevenshteinObjectMapper::new, data);
    }

    @NotNull
    @Override
    protected <R> FieldBinder provideBinder(@NotNull final Gson gson, @NotNull final TypeToken<R> type) {
        return new LevenshteinFieldBinder(
                Arrays.stream(type.getRawType().getDeclaredFields())
                        .map(f -> new FieldBind(gson.fieldNamingStrategy().translateName(f), f))
                        .collect(Collectors.toSet())
        );
    }

    private static class LevenshteinFieldBinder implements FieldBinder {
        private final Set<FieldBind> fields;

        private LevenshteinFieldBinder(@NotNull final Set<FieldBind> fields) {
            this.fields = fields;
        }

        @NotNull
        @Override
        public FieldBind get(@NotNull final String input) {
            return SearchUtils.search(fields, input).get(0);
        }
    }
}
