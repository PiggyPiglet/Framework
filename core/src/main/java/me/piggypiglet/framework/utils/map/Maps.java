package me.piggypiglet.framework.utils.map;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class Maps {
    private Maps() {
        throw new RuntimeException("This class cannot be instantiated.");
    }

    public static <K, V, R> Builder<K, V, R> builder(Map<K, V> implementation, @Nonnull R returnInstance, @Nonnull Consumer<Map<K, V>> build) {
        return new Builder<>(implementation, returnInstance, build);
    }

    public static <K, V> Builder<K, V, Map<K, V>> of() {
        return of(new HashMap<>());
    }

    public static <K, V> Builder<K, V, Map<K, V>> of(Map<K, V> implementation) {
        return new Builder<>(implementation);
    }

    public static final class Builder<K, V, R> {
        private final Map<K, V> map;
        private final R returnInstance;
        private final Consumer<Map<K, V>> build;

        private Builder(Map<K, V> implementation) {
            this(implementation, null, null);
        }

        private Builder(Map<K, V> implementation, R returnInstance, Consumer<Map<K, V>> build) {
            map = implementation;
            this.returnInstance = returnInstance;
            this.build = build;
        }

        public ValueBuilder key(K key) {
            return new ValueBuilder(key);
        }

        public final class ValueBuilder {
            private final K key;

            private ValueBuilder(K key) {
                this.key = key;
            }

            public Builder<K, V, R> value(V value) {
                map.put(key, value);
                return Builder.this;
            }
        }

        @SuppressWarnings("unchecked")
        public R build() {
            if (returnInstance != null && build != null) {
                build.accept(map);
                return returnInstance;
            }

            return (R) map;
        }
    }
}