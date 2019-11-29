package me.piggypiglet.framework.bukkit.commands.framework;

import me.piggypiglet.framework.bukkit.user.BukkitUser;

public abstract class BukkitCommand extends GenericBukkitCommand<BukkitUser> {
    protected BukkitCommand(String command) {
        super(command);
    }
}
