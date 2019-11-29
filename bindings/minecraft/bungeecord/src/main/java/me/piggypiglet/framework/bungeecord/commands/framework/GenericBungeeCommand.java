package me.piggypiglet.framework.bungeecord.commands.framework;

import me.piggypiglet.framework.bungeecord.user.BungeeUser;
import me.piggypiglet.framework.minecraft.commands.framework.GenericMinecraftCommand;

public abstract class GenericBungeeCommand<T extends BungeeUser> extends GenericMinecraftCommand<T> {
    protected GenericBungeeCommand(String command) {
        super(command);
    }
}
