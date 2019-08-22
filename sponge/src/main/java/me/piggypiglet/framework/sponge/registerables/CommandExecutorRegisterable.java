package me.piggypiglet.framework.sponge.registerables;

import com.google.inject.Inject;
import me.piggypiglet.framework.Framework;
import me.piggypiglet.framework.access.AccessManager;
import me.piggypiglet.framework.registerables.StartupRegisterable;
import me.piggypiglet.framework.sponge.commands.SpongeCommandExecutor;
import me.piggypiglet.framework.utils.annotations.Main;
import org.spongepowered.api.Sponge;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class CommandExecutorRegisterable extends StartupRegisterable {
    @Inject private AccessManager accessManager;
    @Inject @Main private Object main;
    @Inject private SpongeCommandExecutor spongeCommandExecutor;
    @Inject private Framework framework;

    @Override
    protected void execute() {
        accessManager.newHandler("sponge", injector);

        Sponge.getCommandManager().register(main, spongeCommandExecutor, framework.getCommandPrefix());
    }
}
