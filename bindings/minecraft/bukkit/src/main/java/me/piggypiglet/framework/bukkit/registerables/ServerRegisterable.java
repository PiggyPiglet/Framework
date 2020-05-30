package me.piggypiglet.framework.bukkit.registerables;

import com.google.inject.TypeLiteral;
import me.piggypiglet.framework.bukkit.binding.server.BukkitServer;
import me.piggypiglet.framework.minecraft.api.key.framework.keyable.Keyable;
import me.piggypiglet.framework.minecraft.api.server.Server;
import me.piggypiglet.framework.registerables.StartupRegisterable;
import me.piggypiglet.framework.utils.builder.GenericBuilder;
import org.bukkit.Bukkit;

public final class ServerRegisterable extends StartupRegisterable {
    @Override
    protected void execute() {
        addBinding(new TypeLiteral<Server<?>>(){}, GenericBuilder.of(() -> new BukkitServer(Bukkit.getServer()))
                .with(Keyable::setup)
                .build());
    }
}
