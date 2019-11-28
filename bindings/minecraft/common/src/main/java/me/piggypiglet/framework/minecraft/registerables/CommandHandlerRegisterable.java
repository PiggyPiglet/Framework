package me.piggypiglet.framework.minecraft.registerables;

import com.google.inject.Inject;
import me.piggypiglet.framework.commands.CommandHandlers;
import me.piggypiglet.framework.minecraft.commands.MinecraftCommandHandler;
import me.piggypiglet.framework.registerables.StartupRegisterable;

public final class CommandHandlerRegisterable extends StartupRegisterable {
    @Inject private CommandHandlers commandHandlers;

    @Override
    protected void execute() {
        commandHandlers.newHandler("minecraft", MinecraftCommandHandler.class, injector);
    }
}
