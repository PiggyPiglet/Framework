package me.piggypiglet.framework.logging;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import me.piggypiglet.framework.logging.implementations.DefaultLogger;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class LoggerFactory {
    @Inject @Named("logger") private static Class logger;

    public static Logger getLogger(String name) {
        try {
            return ((Logger) logger.getConstructors()[0].newInstance()).create(name);
        } catch (Exception ignored) {}

        return new DefaultLogger().create(name);
    }
}
