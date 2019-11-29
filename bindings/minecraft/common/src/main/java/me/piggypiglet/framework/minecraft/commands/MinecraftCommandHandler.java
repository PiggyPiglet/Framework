package me.piggypiglet.framework.minecraft.commands;

import me.piggypiglet.framework.commands.CommandHandler;
import me.piggypiglet.framework.commands.framework.Command;
import me.piggypiglet.framework.minecraft.commands.framework.GenericMinecraftCommand;
import me.piggypiglet.framework.minecraft.lang.Lang;
import me.piggypiglet.framework.minecraft.user.MinecraftUser;
import me.piggypiglet.framework.user.User;

public final class MinecraftCommandHandler extends CommandHandler {
    @Override
    protected boolean run(User user, Command command) {
        if (user instanceof MinecraftUser && command instanceof GenericMinecraftCommand) {
            final MinecraftUser mcUser = (MinecraftUser) user;
            final GenericMinecraftCommand mcCommand = (GenericMinecraftCommand) command;

            if (mcCommand.isPlayerOnly() && !mcUser.isPlayer()) {
                user.sendMessage(Lang.PLAYER_ONLY);
                return false;
            }

            if (mcCommand.isConsoleOnly() && !mcUser.isConsole()) {
                user.sendMessage(Lang.CONSOLE_ONLY);
                return false;
            }
        }

        if (command instanceof GenericMinecraftCommand && !(user instanceof MinecraftUser)) {
            user.sendMessage(Lang.NOT_MINECRAFT_USER);
            return false;
        }

        return super.run(user, command);
    }
}
