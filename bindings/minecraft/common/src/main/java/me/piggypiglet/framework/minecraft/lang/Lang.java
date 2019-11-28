package me.piggypiglet.framework.minecraft.lang;

import me.piggypiglet.framework.lang.LangEnum;

public enum Lang implements LangEnum {
    PLAYER_ONLY("commands.player-only"),
    CONSOLE_ONLY("commands.console-only"),
    NOT_MINECRAFT_USER("commands.not-minecraft-user");

    private final String path;

    Lang(String path) {
        this.path = path;
    }

    @Override
    public String getPath() {
        return path;
    }
}
