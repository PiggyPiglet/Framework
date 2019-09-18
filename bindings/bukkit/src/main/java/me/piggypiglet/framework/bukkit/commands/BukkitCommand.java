package me.piggypiglet.framework.bukkit.commands;

import me.piggypiglet.framework.commands.Command;

public abstract class BukkitCommand extends Command {
    protected Options options = new Options();
    protected boolean playerOnly = false;

    protected BukkitCommand(String command) {
        super(command);
    }

    public boolean isPlayerOnly() {
        return playerOnly;
    }

    protected class Options extends Command.Options {
        public Options playerOnly(boolean playerOnly) {
            BukkitCommand.this.playerOnly = playerOnly;
            return this;
        }
    }
}
