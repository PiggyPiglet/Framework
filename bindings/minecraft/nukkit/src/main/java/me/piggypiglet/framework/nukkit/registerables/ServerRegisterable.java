package me.piggypiglet.framework.nukkit.registerables;

import com.google.inject.TypeLiteral;
import me.piggypiglet.framework.minecraft.api.key.framework.keyable.Keyable;
import me.piggypiglet.framework.minecraft.api.server.Server;
import me.piggypiglet.framework.nukkit.binding.server.NukkitServer;
import me.piggypiglet.framework.registerables.StartupRegisterable;
import me.piggypiglet.framework.utils.builder.GenericBuilder;

public final class ServerRegisterable extends StartupRegisterable {
    @Override
    protected void execute() {
        addBinding(new TypeLiteral<Server<?>>(){}, GenericBuilder.of(() -> new NukkitServer(cn.nukkit.Server.getInstance()))
                .with(Keyable::setup)
                .build());
    }
}