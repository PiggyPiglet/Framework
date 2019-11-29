package me.piggypiglet.framework.minecraft.commands.framework;

import me.piggypiglet.framework.commands.framework.Command;
import me.piggypiglet.framework.minecraft.user.MinecraftUser;

public abstract class GenericMinecraftCommand<T extends MinecraftUser> extends Command<T> {
    private boolean playerOnly = false;
    private boolean consoleOnly = false;

    protected final Options options = new Options();

    protected GenericMinecraftCommand(String command) {
        super(command);
    }

    public boolean isPlayerOnly() {
        return playerOnly;
    }

    public boolean isConsoleOnly() {
        return consoleOnly;
    }

    public final class Options {
        private final GenericMinecraftCommand<T> instance = GenericMinecraftCommand.this;

        public Command<T>.Options root() {
            return GenericMinecraftCommand.super.options;
        }

        public Command<T>.Options playerOnly(boolean value) {
            instance.playerOnly = value;
            return root();
        }

        public Command<T>.Options consoleOnly(boolean value) {
            instance.consoleOnly = value;
            return root();
        }
    }
}
