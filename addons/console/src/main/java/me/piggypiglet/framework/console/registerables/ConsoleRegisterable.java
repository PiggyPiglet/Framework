package me.piggypiglet.framework.console.registerables;

import com.google.inject.Inject;
import me.piggypiglet.framework.access.AccessManager;
import me.piggypiglet.framework.commands.CommandHandlers;
import me.piggypiglet.framework.console.user.ConsoleUser;
import me.piggypiglet.framework.logging.LoggerFactory;
import me.piggypiglet.framework.registerables.StartupRegisterable;
import me.piggypiglet.framework.task.Task;
import me.piggypiglet.framework.user.User;

import java.util.Scanner;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class ConsoleRegisterable extends StartupRegisterable {
    @Inject private Task task;
    @Inject private AccessManager accessManager;
    @Inject private CommandHandlers commandHandlers;

    private static final User USER = new ConsoleUser();

    @Override
    protected void execute() {
        LoggerFactory.getLogger("Console").info("Initializing console module.");

        commandHandlers.newHandler("console", injector);

        task.async(r -> {
            Scanner input = new Scanner(System.in);

            while (true) {
                accessManager.processCommand("console", USER, input.nextLine().toLowerCase());
            }
        });
    }
}
