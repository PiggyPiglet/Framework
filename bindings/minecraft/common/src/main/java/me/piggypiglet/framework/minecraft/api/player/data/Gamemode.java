package me.piggypiglet.framework.minecraft.api.player.data;

import org.jetbrains.annotations.NotNull;

public enum Gamemode {
    SURVIVAL(0),
    CREATIVE(1),
    ADVENTURE(2),
    SPECTATOR(3),
    UNKNOWN(-1);

    private final int id;

    Gamemode(final int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @NotNull
    public static Gamemode fromId(final int id) {
        for (Gamemode gamemode : values()) {
            if (gamemode.id == id) {
                return gamemode;
            }
        }

        return UNKNOWN;
    }
}
