package me.piggypiglet.framework.minecraft.api.key.framework.keyable;

import me.piggypiglet.framework.logging.LoggerFactory;
import me.piggypiglet.framework.logging.framework.Logger;
import me.piggypiglet.framework.minecraft.api.key.Keys;
import me.piggypiglet.framework.minecraft.api.key.framework.Key;
import me.piggypiglet.framework.minecraft.api.key.framework.KeyImpl;
import me.piggypiglet.framework.utils.builder.GenericBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

public abstract class Keyable<H> {
    private static final Logger<?> LOGGER = LoggerFactory.getLogger("Keyable");

    private final KeyGroup[] keys;
    private final KeyGroup unknown;

    protected final Map<KeyGroup, KeyImpl<?, H>> keyFunctions = new HashMap<>();

    protected <E extends Enum<E> & KeyGroup> Keyable(@NotNull final KeyGroup[] keys, @NotNull final E unknown) {
        this.keys = keys;
        this.unknown = unknown;
    }

    @SuppressWarnings("unchecked")
    public void setup() {
        GenericBuilder.of(() -> keyFunctions)
                .with(map -> map.put(unknown, (KeyImpl<?, H>) Keys.UNKNOWN))
                .with(Map::putAll, provideKeyFunctions())
                .build();

        final String difference = GenericBuilder.of(() -> new ArrayList<>(Arrays.asList(keys)))
                .with(List::remove, unknown)
                .with(List::removeAll, keyFunctions.keySet())
                .build()
                .stream()
                .map(Object::toString)
                .collect(Collectors.joining(", "));

        if (!difference.isEmpty()) {
            LOGGER.warning("The following keys haven't been implemented for the %s keyable implementation: %s",
                    getHandle().getClass(), difference);
        }
    }

    public void setup(@NotNull final Map<KeyGroup, KeyImpl<?, H>> keyFunctions) {
        this.keyFunctions.putAll(keyFunctions);
    }

    @NotNull
    public final Map<KeyGroup, KeyImpl<?, H>> getKeyFunctions() {
        return keyFunctions;
    }

    @NotNull
    protected abstract Map<KeyGroup, KeyImpl<?, H>> provideKeyFunctions();

    @SuppressWarnings("unchecked")
    @NotNull
    public <V> Optional<V> get(@NotNull final Key<V, ?> key) {
        return (Optional<V>) keyFunctions.get(
                Arrays.stream(keys)
                        .filter(storedKey -> storedKey.getParent() == key.getName())
                        .findAny().orElse(unknown)
        ).get(getHandle());
    }

    @NotNull
    public abstract H getHandle();
}
