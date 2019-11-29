package me.piggypiglet.framework.bungeecord.commands.framework;

import me.piggypiglet.framework.bungeecord.user.BungeeUser;

public abstract class BungeeCommand extends GenericBungeeCommand<BungeeUser> {
    protected BungeeCommand(String command) {
        super(command);
    }
}
