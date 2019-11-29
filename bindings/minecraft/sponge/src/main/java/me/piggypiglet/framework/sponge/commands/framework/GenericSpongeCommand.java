package me.piggypiglet.framework.sponge.commands.framework;

import me.piggypiglet.framework.minecraft.commands.framework.GenericMinecraftCommand;
import me.piggypiglet.framework.sponge.user.SpongeUser;

public abstract class GenericSpongeCommand<T extends SpongeUser> extends GenericMinecraftCommand<T> {
    protected GenericSpongeCommand(String command) {
        super(command);
    }
}
