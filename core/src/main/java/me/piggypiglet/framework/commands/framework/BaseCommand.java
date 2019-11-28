package me.piggypiglet.framework.commands.framework;

import me.piggypiglet.framework.user.User;

public abstract class BaseCommand extends Command<User> {
    protected BaseCommand(String command) {
        super(command);
    }
}
