package me.piggypiglet.framework.minecraft.commands;

import me.piggypiglet.framework.commands.framework.Command;
import me.piggypiglet.framework.minecraft.user.MinecraftUser;

public abstract class MinecraftCommand extends Command<MinecraftUser> {
    private boolean playerOnly = false;
    private boolean consoleOnly = false;

    protected final Options options = new Options();

    protected MinecraftCommand(String command) {
        super(command);
    }

    public boolean isPlayerOnly() {
        return playerOnly;
    }

    public boolean isConsoleOnly() {
        return consoleOnly;
    }

    public final class Options {
        private final MinecraftCommand instance = MinecraftCommand.this;

        public Command.Options root() {
            return MinecraftCommand.super.options;
        }

        public Command.Options playerOnly(boolean value) {
            instance.playerOnly = value;
            return root();
        }

        public Command.Options consoleOnly(boolean value) {
            instance.consoleOnly = value;
            return root();
        }
    }
}
