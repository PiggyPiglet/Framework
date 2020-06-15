package me.piggypiglet.framework.sponge.registerables;

import com.google.inject.TypeLiteral;
import me.piggypiglet.framework.minecraft.api.key.framework.keyable.Keyable;
import me.piggypiglet.framework.minecraft.api.server.Server;
import me.piggypiglet.framework.registerables.StartupRegisterable;
import me.piggypiglet.framework.sponge.binding.server.SpongeServer;
import me.piggypiglet.framework.utils.builder.GenericBuilder;
import org.spongepowered.api.Sponge;

public final class ServerRegisterable extends StartupRegisterable {
    @Override
    protected void execute() {
        addBinding(new TypeLiteral<Server<?>>(){}, GenericBuilder.of(() -> new SpongeServer(Sponge.getServer()))
                .with(Keyable::setup)
                .build());
    }
}
