package me.piggypiglet.framework.utils.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public final class GenericBuilder {
    private GenericBuilder() {
        throw new RuntimeException("This class cannot be instantiated.");
    }

    public static <T> Builder<T> of(Supplier<T> initialiser) {
        return new Builder<>(initialiser);
    }

    public static class Builder<T> {
        private final Supplier<T> initialiser;
        private final List<Consumer<T>> instanceModifiers = new ArrayList<>();

        private Builder(Supplier<T> initialiser) {
            this.initialiser = initialiser;
        }

        public <U> Builder<T> with(BiConsumer<T, U> consumer, U value) {
            instanceModifiers.add(instance -> consumer.accept(instance, value));
            return this;
        }

        public T build() {
            T value = initialiser.get();
            instanceModifiers.forEach(modifier -> modifier.accept(value));
            instanceModifiers.clear();
            return value;
        }
    }
}
