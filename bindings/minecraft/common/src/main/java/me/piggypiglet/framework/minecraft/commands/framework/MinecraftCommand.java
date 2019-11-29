package me.piggypiglet.framework.minecraft.commands.framework;

import me.piggypiglet.framework.minecraft.user.MinecraftUser;

public abstract class MinecraftCommand extends GenericMinecraftCommand<MinecraftUser> {
    protected MinecraftCommand(String command) {
        super(command);
    }
}
