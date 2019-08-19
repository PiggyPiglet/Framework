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

    private List<String> handlers = new ArrayList<>();
    private String description = "null";
    private String usage = "<required args> [optional args]";
    private List<String> permissions = new ArrayList<>();
    private boolean def = false;

    protected Options options = new Options();

    protected Command(String command) {
        this.command = command;
    }

    /**
     *
     * @param user Wrapper for any sort of user interaction
     * @param args Message, excluding the prefix and command, split via space. Multiple word arguments can be achieved by using double quotes in the user's input. For example, test "multi word" would only be two arguments.
     * @return Whether the command's usage method should be sent or not. Returning false will send the message.
     */
    protected abstract boolean execute(User user, String[] args);

    public boolean run(User user, String[] args) {
        return execute(user, args);
    }

    public String getCommand() {
        return command;
    }

    public List<String> getHandlers() {
        return handlers;
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
        /**
         * Which handlers should be able to process this command? Leave empty for it to be runnable on all handlers.
         * @param handlers Handler references
         * @return Options
         */
        public Options handlers(String... handlers) {
            Command.this.handlers = Arrays.asList(handlers);
            return this;
        }

        /**
         * Description that will show for this command in the help command
         * @param description String describing the command, keep it short
         * @return Options
         */
        public Options description(String description) {
            Command.this.description = description;
            return this;
        }

        /**
         * Command's usage, should be self explanatory, but an example is &lt;required args&gt; [optional args].
         * @param usage String
         * @return Options
         */
        public Options usage(String usage) {
            Command.this.usage = usage;
            return this;
        }

        /**
         * What permissions will the User need to run this command? Leave empty for no permissions needed.
         * @param permissions Permissions
         * @return Options
         */
        public Options permissions(String... permissions) {
            Command.this.permissions = Arrays.asList(permissions);
            return this;
        }

        /**
         * Is this a default command? For example a help command.
         * @param value Boolean
         * @return Options
         */
        public Options def(boolean value) {
            def = value;
            return this;
        }
    }
}
