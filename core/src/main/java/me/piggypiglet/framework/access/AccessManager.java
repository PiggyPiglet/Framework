package me.piggypiglet.framework.access;

import com.google.inject.Inject;
import me.piggypiglet.framework.commands.CommandHandler;
import me.piggypiglet.framework.task.Task;
import me.piggypiglet.framework.user.User;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class AccessManager {
    @Inject private Task task;
    @Inject private CommandHandler commandHandler;

    public void processCommand(User user, String message) {
        task.async(r -> commandHandler.process(user, message));
    }
}
