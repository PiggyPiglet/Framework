package me.piggypiglet.framework.velocity.commands.framework;

import me.piggypiglet.framework.minecraft.commands.framework.GenericMinecraftCommand;
import me.piggypiglet.framework.velocity.user.VelocityUser;

public abstract class GenericVelocityCommand<T extends VelocityUser> extends GenericMinecraftCommand<T> {
    protected GenericVelocityCommand(String command) {
        super(command);
    }
}
