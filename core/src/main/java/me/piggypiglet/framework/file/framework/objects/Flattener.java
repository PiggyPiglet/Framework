package me.piggypiglet.framework.file.framework.objects;

import me.piggypiglet.framework.utils.map.Maps;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

public final class Flattener {
    private final Class<?> clazz;
    private final Function<?, Map<String, Object>> flattener;

    private Flattener(Class<?> clazz, Function<?, Map<String, Object>> flattener) {
        this.clazz = clazz;
        this.flattener = flattener;
    }

    public static <T> Builder<T> builder(Class<T> clazz) {
        return new Builder<>(clazz);
    }

    public static class Builder<T> {
        private final Class<T> clazz;
        private Function<T, Map<String, Object>> flattener = null;

        private Builder(Class<T> clazz) {
            this.clazz = clazz;
        }

        public Builder<T> flattener(Function<T, Map<String, Object>> flattener) {
            this.flattener = flattener;
            return this;
        }

        public Flattener build() {
            return new Flattener(clazz, flattener);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> Stream<Map.Entry<String, Object>> flatten(Map.Entry<String, Object> entry) {
        if (clazz == null || flattener == null) {
            return Maps.flatten(entry);
        }

        return Maps.flatten(entry, (Class<T>) clazz, (Function<T, Map<String, Object>>) flattener);
    }
}
