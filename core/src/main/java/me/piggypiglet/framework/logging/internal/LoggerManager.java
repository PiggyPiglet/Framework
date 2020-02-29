package me.piggypiglet.framework.logging.internal;

import com.google.inject.Singleton;
import me.piggypiglet.framework.logging.framework.Logger;
import me.piggypiglet.framework.managers.Manager;
import me.piggypiglet.framework.managers.objects.KeyTypeInfo;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Singleton
public final class LoggerManager extends Manager<Logger<?>> {
    private final Map<String, Logger<?>> loggers = new HashMap<>();

    @NotNull
    @Override
    protected KeyTypeInfo configure(@NotNull final KeyTypeInfo.Builder builder) {
        return builder
                .key(String.class)
                        .map(loggers)
                .build();
    }

    @Override
    protected void insert(@NotNull final Logger<?> item) {
        loggers.put(item.getName(), item);
    }

    @Override
    protected void delete(@NotNull final Logger<?> item) {
        loggers.remove(item.getName());
    }

    @NotNull
    @Override
    protected Collection<Logger<?>> retrieveAll() {
        return loggers.values();
    }
}
