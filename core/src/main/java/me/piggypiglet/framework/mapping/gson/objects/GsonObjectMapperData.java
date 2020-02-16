package me.piggypiglet.framework.mapping.gson.objects;

import com.google.gson.Gson;
import org.jetbrains.annotations.NotNull;

public final class GsonObjectMapperData {
    private final Class<?> type;
    private final Gson gson;

    public GsonObjectMapperData(@NotNull final Class<?> type, @NotNull final Gson gson) {
        this.type = type;
        this.gson = gson;
    }

    public Class<?> getType() {
        return type;
    }

    public Gson getGson() {
        return gson;
    }
}
