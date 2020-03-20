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

/**
 * GSON Object Mapper which utilises the Levenshtein distance algorithm
 * for key-field matching. Uses the algorithm via a port of python's
 * fuzzy wuzzy library, specifically the weighted ratio utility.
 *
 * @param <T> Type this mapper maps to/from
 */
public final class LevenshteinObjectMapper<T> extends GsonObjectMapper<T> {
    public LevenshteinObjectMapper(@NotNull final GsonObjectMapperData data) {
        super(LevenshteinObjectMapper::new, data);
    }

    /**
     * Returns an instance of the LevenshteinFieldBinder, for the provided
     * type. Provides the binder with all the fields in the class, from which,
     * the binder's #get method then uses StringUtils.search to filter through
     * the fields and find the best match.
     *
     * @param gson Gson instance
     * @param type Requested type
     * @param <R> Type to map
     * @return LevenshteinFieldBinder
     */
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
