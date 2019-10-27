package me.piggypiglet.framework.minecraft.user;

import me.piggypiglet.framework.minecraft.player.Player;
import me.piggypiglet.framework.user.User;

public abstract class MinecraftUser extends User {
    /**
     * Provide basic info to the superclass
     *
     * @param name Name of the user
     * @param id   ID of the user
     */
    protected MinecraftUser(String name, String id) {
        super(name, id);
    }

    public abstract boolean isPlayer();

    public abstract Player getAsPlayer();
}
