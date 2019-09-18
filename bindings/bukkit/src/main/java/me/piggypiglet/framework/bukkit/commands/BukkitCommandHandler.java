package me.piggypiglet.framework.bukkit.commands;

import me.piggypiglet.framework.bukkit.user.BukkitUser;
import me.piggypiglet.framework.commands.Command;
import me.piggypiglet.framework.commands.CommandHandler;
import me.piggypiglet.framework.user.User;

public final class BukkitCommandHandler extends CommandHandler {
    @Override
    protected boolean process(User user, Command command) {
        if (user instanceof BukkitUser && command instanceof BukkitCommand) {
            if (((BukkitCommand) command).isPlayerOnly()) {
                if (((BukkitUser) user).isPlayer()) {
                    return true;
                } else {
                    user.sendMessage("Only player's can execute this command.");
                    return false;
                }
            }
        }

        return true;
    }
}
