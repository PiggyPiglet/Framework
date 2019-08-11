package me.piggypiglet.framework.commands;

import com.google.inject.Inject;
import me.piggypiglet.framework.Framework;
import me.piggypiglet.framework.commands.implementations.HelpCommand;
import me.piggypiglet.framework.user.User;

import java.util.Arrays;
import java.util.List;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class CommandHandler {
    @Inject private Framework framework;
    @Inject private HelpCommand defHelpCommand;

    private List<Command> commands;
    private Command helpCommand;

    public void process(User user, String message) {
        if (message.startsWith(framework.getCommandPrefix())) {
            message = message.replaceFirst(framework.getCommandPrefix(), "").trim();

            if (message.isEmpty()) {
                helpCommand.run(user, new String[]{});
                return;
            }

            for (Command c : commands) {
                String cmd = c.getCommand();

                if (message.toLowerCase().startsWith(cmd.toLowerCase())) {
                    List<String> permissions = c.getPermissions();

                    if (permissions.size() == 0 || permissions.stream().anyMatch(user::hasPermission)) {
                        String[] args = args(message);

                        if (!c.run(user, args)) {
                            user.sendMessage("Incorrect usage, correct usage is: %s %s", cmd, c.getUsage());
                        }
                    } else {
                        user.sendMessage("You do not have permission for that command.");
                    }

                    return;
                }
            }

            user.sendMessage("Unknown command.");
        }
    }

    public void setCommands(List<Command> commands) {
        this.commands = commands;
        helpCommand = commands.stream().filter(Command::isDefault).findFirst().orElse(defHelpCommand);
    }

    public List<Command> getCommands() {
        return commands;
    }

    private String[] args(String text) {
        String[] args = text.trim().split("\\s+(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
        args = Arrays.copyOfRange(args, 1, args.length);

        return args.length == 0 ? new String[]{} : args;
    }
}
