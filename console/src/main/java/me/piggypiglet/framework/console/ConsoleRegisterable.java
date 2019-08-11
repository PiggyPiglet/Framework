package me.piggypiglet.framework.console;

import com.google.inject.Inject;
import me.piggypiglet.framework.access.AccessManager;
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

    private static final User USER = new ConsoleUser();

    @Override
    protected void execute() {
        task.async(r -> {
            Scanner input = new Scanner(System.in);

            while (true) {
                accessManager.processCommand(USER, input.nextLine().toLowerCase());
            }
        });
    }
}
