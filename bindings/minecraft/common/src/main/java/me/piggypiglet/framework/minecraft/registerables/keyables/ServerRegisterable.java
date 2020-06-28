package me.piggypiglet.framework.minecraft.registerables.keyables;

import com.google.inject.TypeLiteral;
import me.piggypiglet.framework.minecraft.api.key.framework.keyable.Keyable;
import me.piggypiglet.framework.minecraft.api.key.framework.keyable.KeyableInitializer;
import me.piggypiglet.framework.minecraft.api.server.Server;
import me.piggypiglet.framework.registerables.StartupRegisterable;
import me.piggypiglet.framework.utils.annotations.internal.Internal;

import javax.inject.Inject;
import java.util.Map;

// ------------------------------
// Copyright (c) PiggyPiglet 2020
// https://www.piggypiglet.me
// ------------------------------
public final class ServerRegisterable extends StartupRegisterable {
    private static final TypeLiteral<Server<?>> SERVER_TYPE = new TypeLiteral<Server<?>>(){};

    @Inject @Internal("keyable-initializers") private Map<Class<? extends Keyable<?>>, KeyableInitializer<? extends Keyable<?>>> keyableInitializers;

    @Override
    protected void execute() {
        addBinding(SERVER_TYPE, (Server<?>) keyableInitializers.get(Server.class).create());
    }
}
