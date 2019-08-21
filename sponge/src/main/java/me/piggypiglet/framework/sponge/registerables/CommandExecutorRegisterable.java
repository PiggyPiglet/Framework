package me.piggypiglet.framework.sponge.registerables;

import com.google.inject.Inject;
import me.piggypiglet.framework.Framework;
import me.piggypiglet.framework.access.AccessManager;
import me.piggypiglet.framework.registerables.StartupRegisterable;
import me.piggypiglet.framework.sponge.commands.SpongeCommandExecutor;
import me.piggypiglet.framework.utils.annotations.Main;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;

import java.util.Arrays;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class CommandExecutorRegisterable extends StartupRegisterable {
    @Inject private SpongeCommandExecutor spongeCommandExecutor;
    @Inject @Main private Object main;
    @Inject private Framework framework;
    @Inject private AccessManager accessManager;

    @Override
    protected void execute() {
        CommandSpec.Builder command = CommandSpec.builder()
                .arguments(GenericArguments.string(Text.of("args")))
                .executor(spongeCommandExecutor);

        accessManager.newHandler("sponge", injector);

        Sponge.getCommandManager().register(main, command.build(), framework.getCommandPrefix());
    }
}
