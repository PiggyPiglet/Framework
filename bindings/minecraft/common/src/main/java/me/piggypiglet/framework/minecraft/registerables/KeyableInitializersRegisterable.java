package me.piggypiglet.framework.minecraft.registerables;

import com.google.inject.TypeLiteral;
import me.piggypiglet.framework.minecraft.api.key.framework.keyable.Initializer;
import me.piggypiglet.framework.minecraft.api.key.framework.keyable.Keyable;
import me.piggypiglet.framework.minecraft.api.key.framework.keyable.KeyableInitializer;
import me.piggypiglet.framework.registerables.StartupRegisterable;
import me.piggypiglet.framework.utils.annotations.internal.Internal;
import me.piggypiglet.framework.utils.annotations.internal.InternalAnnotations;
import me.piggypiglet.framework.utils.builder.GenericBuilder;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

// ------------------------------
// Copyright (c) PiggyPiglet 2020
// https://www.piggypiglet.me
// ------------------------------
public final class KeyableInitializersRegisterable extends StartupRegisterable {
    private static final TypeLiteral<Map<Class<? extends Keyable<?>>, KeyableInitializer<? extends Keyable<?>>>> KEYABLE_INITIALIZERS_TYPE =
            new TypeLiteral<Map<Class<? extends Keyable<?>>, KeyableInitializer<? extends Keyable<?>>>>(){};

    @Inject @Internal("keyables") private Set<Class<? extends Keyable<?>>> keyables;

    @Override
    protected void execute() {
        final Map<Class<? extends Keyable<?>>, KeyableInitializer<? extends Keyable<?>>> keyableInitializers = new HashMap<>();

        for (final Class<? extends Keyable<?>> keyable : keyables) {
            final KeyableInitializer<? extends Keyable<?>> initializer;

            if (keyable.isAnnotationPresent(Initializer.class)) {
                initializer = initializerWrapper(injector.getInstance(keyable.getAnnotation(Initializer.class).value()));
            } else {
                initializer = () -> injector.getInstance(keyable);
            }

            keyableInitializers.put(keyable, initializer);
        }

        addAnnotatedBinding(KEYABLE_INITIALIZERS_TYPE, InternalAnnotations.internal("keyable-initializers"),
                keyableInitializers);
    }

    @NotNull
    private KeyableInitializer<? extends Keyable<?>> initializerWrapper(@NotNull final KeyableInitializer<? extends Keyable<?>> initializer) {
        return () -> GenericBuilder.of(initializer::create)
                .with(injector::injectMembers)
                .build();
    }
}
