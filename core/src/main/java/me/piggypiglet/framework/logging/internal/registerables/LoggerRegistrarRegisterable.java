package me.piggypiglet.framework.logging.internal.registerables;

import com.google.inject.Inject;
import com.google.inject.TypeLiteral;
import me.piggypiglet.framework.logging.LoggerFactory;
import me.piggypiglet.framework.logging.annotations.LoggerName;
import me.piggypiglet.framework.logging.framework.Logger;
import me.piggypiglet.framework.logging.internal.LoggerManager;
import me.piggypiglet.framework.registerables.StartupRegisterable;
import me.piggypiglet.framework.utils.annotations.internal.Internal;

import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

public final class LoggerRegistrarRegisterable extends StartupRegisterable {
    private static final TypeLiteral<Logger<?>> TYPE = new TypeLiteral<Logger<?>>(){};

    @Inject private LoggerManager loggerManager;
    @Inject @Internal("logger_fields") private Set<Field> fields;
    @Inject @Internal("logger_params") private Set<Parameter> parameters;

    @Override
    protected void execute() {
        final Map<LoggerName, Logger<?>> loggers = new HashMap<>();

        Stream.concat(fields.stream(), parameters.stream()).forEach(element -> {
            final LoggerName annotation = element.getAnnotation(LoggerName.class);

            if (!loggers.containsKey(annotation)) {
                loggers.put(annotation, LoggerFactory.getLogger(annotation.value()));
            }
        });

        loggers.forEach((name, logger) -> {
            loggerManager.add(logger);
            addAnnotatedBinding(TYPE, name, logger);
        });
    }
}
