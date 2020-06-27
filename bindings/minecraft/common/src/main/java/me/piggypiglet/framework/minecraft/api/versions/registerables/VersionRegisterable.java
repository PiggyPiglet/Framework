package me.piggypiglet.framework.minecraft.api.versions.registerables;

import com.google.inject.Inject;
import me.piggypiglet.framework.minecraft.api.key.Keys;
import me.piggypiglet.framework.minecraft.api.server.Server;
import me.piggypiglet.framework.minecraft.api.versions.Versions;
import me.piggypiglet.framework.registerables.StartupRegisterable;

public final class VersionRegisterable extends StartupRegisterable {
    @Inject private Server<?> server;

    @Override
    protected void execute() {
        addBinding(server.get(Keys.VERSION).orElse(Versions.getLatest()));
    }
}
