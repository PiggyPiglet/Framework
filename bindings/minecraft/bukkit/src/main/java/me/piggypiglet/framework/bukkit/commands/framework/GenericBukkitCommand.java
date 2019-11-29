package me.piggypiglet.framework.bukkit.commands.framework;

import me.piggypiglet.framework.bukkit.user.BukkitUser;
import me.piggypiglet.framework.minecraft.commands.framework.GenericMinecraftCommand;

public abstract class GenericBukkitCommand<T extends BukkitUser> extends GenericMinecraftCommand<T> {
    protected GenericBukkitCommand(String command) {
        super(command);
    }
}
