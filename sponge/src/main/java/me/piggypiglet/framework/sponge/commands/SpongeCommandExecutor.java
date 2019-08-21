package me.piggypiglet.framework.sponge.commands;

import com.google.inject.Inject;
import me.piggypiglet.framework.access.AccessManager;
import me.piggypiglet.framework.sponge.user.SpongeUser;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandExecutor;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class SpongeCommandExecutor implements CommandExecutor {
    @Inject private AccessManager accessManager;

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        String message = (String) args.getOne("args").orElse("");
        accessManager.processCommand("sponge", new SpongeUser(src), message);
        return CommandResult.success();
    }
}