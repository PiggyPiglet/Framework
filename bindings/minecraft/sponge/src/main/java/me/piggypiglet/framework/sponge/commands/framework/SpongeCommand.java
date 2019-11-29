package me.piggypiglet.framework.sponge.commands.framework;

import me.piggypiglet.framework.sponge.user.SpongeUser;

public abstract class SpongeCommand extends GenericSpongeCommand<SpongeUser> {
    protected SpongeCommand(String command) {
        super(command);
    }
}
