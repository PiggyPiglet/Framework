package me.piggypiglet.framework.registerables.startup.file;

import com.google.inject.Inject;
import me.piggypiglet.framework.file.FileManager;
import me.piggypiglet.framework.file.framework.AbstractFileConfiguration;
import me.piggypiglet.framework.file.mapping.Maps;
import me.piggypiglet.framework.mapper.LevenshteinObjectMapper;
import me.piggypiglet.framework.reflection.Reflections;
import me.piggypiglet.framework.registerables.StartupRegisterable;

public final class FileMappingRegisterable extends StartupRegisterable {
    @Inject private FileManager fileManager;
    @Inject private Reflections reflections;

    @Override
    protected void execute() {
        reflections.getTypesAnnotatedWith(Maps.class).forEach(c -> add(c, c.getAnnotation(Maps.class).value()));
    }

    private <T> void add(Class<T> clazz, String name) {
        AbstractFileConfiguration config = (AbstractFileConfiguration) fileManager.getConfig(name);

        if (config != null) {
            addBinding(clazz, new LevenshteinObjectMapper<T>(clazz){}.dataToType(config.getAll()));
        }
    }
}
