package me.piggypiglet.framework.minecraft.registerables;

import com.google.inject.Inject;
import me.piggypiglet.framework.minecraft.server.DefaultServer;
import me.piggypiglet.framework.minecraft.server.Server;
import me.piggypiglet.framework.registerables.StartupRegisterable;
import me.piggypiglet.framework.scanning.Scanner;

import java.util.Optional;

public final class ServerRegisterable extends StartupRegisterable {
    @Inject private Scanner scanner;

    @SuppressWarnings("unchecked")
    @Override
    protected void execute() {
       addBinding(Server.class, ((Optional<Server>) scanner.getSubTypesOf(Server.class).stream().map(injector::getInstance).findFirst()).orElse(new DefaultServer()));
    }
}
