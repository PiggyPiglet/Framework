package me.piggypiglet.framework.minecraft.api.key.framework.keyable;

import me.piggypiglet.framework.logging.LoggerFactory;
import me.piggypiglet.framework.logging.framework.Logger;
import me.piggypiglet.framework.minecraft.api.key.Keys;
import me.piggypiglet.framework.minecraft.api.key.framework.Key;
import me.piggypiglet.framework.minecraft.api.key.framework.KeyImpl;
import me.piggypiglet.framework.utils.builder.GenericBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class Keyable<H> {
    private static final Logger<?> LOGGER = LoggerFactory.getLogger("Keyable");

    private final Class<? extends KeyEnum> enumClass;
    private final KeyEnum unknown;
    private final Function<H, Keyable<H>> initializer;

    protected final Map<KeyEnum, KeyImpl<?, H>> keyFunctions;

    @SuppressWarnings({"unchecked", "rawtypes"})
    protected <E extends Enum<E> & KeyEnum> Keyable(@NotNull final Class<E> enumClass, @NotNull final E unknown,
                      @NotNull final Function<H, Keyable<H>> initializer) {
        this.enumClass = enumClass;
        this.unknown = unknown;
        this.initializer = initializer;

        keyFunctions = (Map) new EnumMap<>(enumClass);
    }

    @SuppressWarnings("unchecked")
    public <E extends Enum<E> & KeyEnum> void setup() {
        GenericBuilder.of(() -> keyFunctions)
                .with(map -> map.put(unknown, (KeyImpl<?, H>) Keys.UNKNOWN))
                .with(Map::putAll, provideKeyFunctions())
                .build();

        final String difference = GenericBuilder.of(() -> new ArrayList<>(Arrays.asList(((Class<E>) enumClass).getEnumConstants())))
                .with(List::remove, unknown)
                .with(List::removeAll, keyFunctions.keySet())
                .build()
                .stream()
                .map(Enum::toString)
                .collect(Collectors.joining(", "));

        if (!difference.isEmpty()) {
            LOGGER.warning("The following keys haven't been implemented for the %s keyable implementation: %s",
                    getHandle().getClass(), difference);
        }
    }

    private void setup(@NotNull final Map<KeyEnum, KeyImpl<?, H>> keyFunctions) {
        this.keyFunctions.putAll(keyFunctions);
    }

    @NotNull
    public Keyable<H> create(@NotNull final H handle) {
        return GenericBuilder.of(() -> initializer.apply(handle))
                .with(keyable -> keyable.setup(keyFunctions))
                .build();
    }

    @NotNull
    protected abstract Map<KeyEnum, KeyImpl<?, H>> provideKeyFunctions();

    @SuppressWarnings("unchecked")
    @NotNull
    public <E extends Enum<E> & KeyEnum, V> Optional<V> get(@NotNull final Key<V, ?> key) {
        return (Optional<V>) keyFunctions.get(KeyEnum.fromKey((Class<E>) enumClass, key).orElse((E) unknown)).get(getHandle());
    }

    @NotNull
    public abstract H getHandle();
}
