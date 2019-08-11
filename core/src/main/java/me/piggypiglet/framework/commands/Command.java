package me.piggypiglet.framework.commands;

import me.piggypiglet.framework.user.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public abstract class Command {
    private final String command;

    private String description = "null";
    private String usage = "<required args> [optional args]";
    private List<String> permissions = new ArrayList<>();
    private boolean def = false;

    protected Options options = new Options();

    protected Command(String command) {
        this.command = command;
    }

    protected abstract boolean execute(User user, String[] args);

    public boolean run(User user, String[] args) {
        return execute(user, args);
    }

    public String getCommand() {
        return command;
    }

    public String getDescription() {
        return description;
    }

    public String getUsage() {
        return usage;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public boolean isDefault() {
        return def;
    }

    protected class Options {
        protected Options description(String description) {
            Command.this.description = description;
            return this;
        }

        protected Options usage(String usage) {
            Command.this.usage = usage;
            return this;
        }

        protected Options permissions(String... permissions) {
            Command.this.permissions = Arrays.asList(permissions);
            return this;
        }

        protected Options def(boolean value) {
            Command.this.def = value;
            return this;
        }
    }
}
