package me.piggypiglet.framework.commands;

import lombok.Getter;
import lombok.Setter;

// ------------------------------
// Copyright (c) PiggyPiglet 2018
// https://www.piggypiglet.me
// ------------------------------
public abstract class Command {
    @Getter private final String[] commands;
    @Getter @Setter private String[] permissions;
    @Getter @Setter private String usage;

    protected Command(String... commands) {
        this.commands = commands;
    }

    protected abstract void sendMessage(String key, Object... variables);

    protected abstract void
}
