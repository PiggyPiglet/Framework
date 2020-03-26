package me.piggypiglet.framework.minecraft.api.key.framework.keyable;

import me.piggypiglet.framework.logging.LoggerFactory;
import me.piggypiglet.framework.logging.framework.Logger;
import me.piggypiglet.framework.minecraft.api.key.Keys;
import me.piggypiglet.framework.minecraft.api.key.framework.Key;
import me.piggypiglet.framework.minecraft.api.key.framework.KeyImpl;
import me.piggypiglet.framework.utils.builder.GenericBuilder;
import me.piggypiglet.framework.utils.map.Maps;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

public abstract class Keyable<E extends Enum<E> & KeyEnum, H> {
    private static final Logger<?> LOGGER = LoggerFactory.getLogger("Keyable");

    private final Class<E> enumClass;
    private final E unknown;

    protected final Map<E, KeyImpl<?, H>> keyFunctions;

    protected Keyable(@NotNull final Class<E> enumClass, @NotNull final E unknown) {
        this.enumClass = enumClass;
        this.unknown = unknown;

        keyFunctions = Maps.of(new EnumMap<E, KeyImpl<?, H>>(enumClass))
                .key(unknown).value(Keys.UNKNOWN)
                .build();
    }

    public void setup() {
        keyFunctions.putAll(provideKeyFunctions());
        final String difference = GenericBuilder.of(() -> new ArrayList<>(Arrays.asList(enumClass.getEnumConstants())))
                .with(list -> list.remove(unknown))
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

    @NotNull
    protected abstract Map<E, KeyImpl<?, H>> provideKeyFunctions();

    @SuppressWarnings("unchecked")
    @NotNull
    public <V> Optional<V> get(@NotNull final Key<V, ?> key) {
        return (Optional<V>) keyFunctions.get(KeyEnum.fromKey(enumClass, key).orElse(unknown)).get(getHandle());
    }

    @NotNull
    public abstract H getHandle();
}
