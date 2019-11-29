package me.piggypiglet.framework.velocity.commands.framework;

import me.piggypiglet.framework.velocity.user.VelocityUser;

public abstract class VelocityCommand extends GenericVelocityCommand<VelocityUser> {
    protected VelocityCommand(String command) {
        super(command);
    }
}
