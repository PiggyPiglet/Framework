package me.piggypiglet.framework.nukkit.commands.framework;

import me.piggypiglet.framework.minecraft.commands.framework.GenericMinecraftCommand;
import me.piggypiglet.framework.nukkit.user.NukkitUser;

public abstract class GenericNukkitCommand<T extends NukkitUser> extends GenericMinecraftCommand<T> {
    protected GenericNukkitCommand(String command) {
        super(command);
    }
}
