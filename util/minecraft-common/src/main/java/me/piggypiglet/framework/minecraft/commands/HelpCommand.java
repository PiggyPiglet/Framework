package me.piggypiglet.framework.minecraft.commands;

import com.google.inject.Inject;
import me.piggypiglet.framework.Framework;
import me.piggypiglet.framework.commands.Command;
import me.piggypiglet.framework.commands.CommandHandlers;
import me.piggypiglet.framework.user.User;

import java.util.List;
import java.util.Optional;

public final class HelpCommand extends Command {
    @Inject private CommandHandlers commandHandlers;
    @Inject private Framework framework;

    public HelpCommand() {
        super("help");
        options.description("Get a description of all commands or a specific one.").usage("[command]").def(true);
    }

    @Override
    protected boolean execute(User user, String[] args) {
        final List<Command> commands = commandHandlers.getCommands();

        if (args.length > 0) {
            final Optional<Command> command = commands.stream().filter(c -> c.getCommand().equalsIgnoreCase(args[0])).findAny();

            if (command.isPresent()) {
                final Command cmd = command.get();
                user.sendMessage("&c/%s %s%s &8- &7%s\n",
                        framework.getCommandPrefix(),
                        cmd.getCommand(),
                        cmd.getUsage().isEmpty() ? "" : " " + cmd.getUsage(),
                        cmd.getDescription());
            } else {
                user.sendMessage("&cThat command does not exist.");
            }

            return true;
        }

        final StringBuilder builder = new StringBuilder("&7Help menu");

        commands.forEach(c -> {
            builder.append("&c/").append(framework.getCommandPrefix()).append(" ").append(c.getCommand());

            if (!c.getUsage().isEmpty()) {
                builder.append(" ").append(c.getUsage());
            }

            builder.append(" &8- &7").append(c.getDescription()).append("\n");
        });

        user.sendMessage(builder.toString());

        return true;
    }
}
