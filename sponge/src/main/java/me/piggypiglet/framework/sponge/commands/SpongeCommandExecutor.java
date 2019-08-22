package me.piggypiglet.framework.sponge.commands;

import com.google.inject.Inject;
import me.piggypiglet.framework.Framework;
import me.piggypiglet.framework.access.AccessManager;
import me.piggypiglet.framework.sponge.user.SpongeUser;
import org.spongepowered.api.command.CommandCallable;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class SpongeCommandExecutor implements CommandCallable {
    @Inject private AccessManager accessManager;
    @Inject private Framework framework;

    @Override
    public CommandResult process(CommandSource source, String arguments) throws CommandException {
        accessManager.processCommand("sponge", new SpongeUser(source), framework.getCommandPrefix() + " " + arguments);
        return CommandResult.success();
    }

    @Override
    public List<String> getSuggestions(CommandSource source, String arguments, @Nullable Location<World> targetPosition) throws CommandException {
        return Collections.emptyList();
    }

    @Override
    public boolean testPermission(CommandSource source) {
        return true;
    }

    @Override
    public Optional<Text> getShortDescription(CommandSource source) {
        return Optional.of(Text.of("Main command."));
    }

    @Override
    public Optional<Text> getHelp(CommandSource source) {
        return Optional.of(Text.of("Main command."));
    }

    @Override
    public Text getUsage(CommandSource source) {
        return Text.of("<required args> [optional args]");
    }
}