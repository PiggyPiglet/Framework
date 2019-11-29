package me.piggypiglet.framework.nukkit.commands.framework;

import me.piggypiglet.framework.nukkit.user.NukkitUser;

public abstract class NukkitCommand extends GenericNukkitCommand<NukkitUser> {
    protected NukkitCommand(String command) {
        super(command);
    }
}
