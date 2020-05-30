package me.piggypiglet.framework.minecraft.registerables;

import com.google.inject.Inject;
import me.piggypiglet.framework.minecraft.api.server.Server;
import me.piggypiglet.framework.minecraft.registerables.exceptions.MissingImplementationException;
import me.piggypiglet.framework.registerables.StartupRegisterable;
import me.piggypiglet.framework.utils.annotations.internal.Internal;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public final class ServerRegisterable extends StartupRegisterable {
    private final Class<? extends Server> server;

    @Inject
    public ServerRegisterable(@NotNull @Internal("server-implementation") final Set<Class<? extends Server>> implementations) {
        this.server = implementations.stream().findAny()
                .orElseThrow(() -> new MissingImplementationException("An implementation for the Server API couldn't be found"));
    }

    @Override
    protected void execute() {
        addBinding(Server.class, injector.getInstance(server));
    }
}
