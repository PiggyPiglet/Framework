/*
 * MIT License
 *
 * Copyright (c) 2019 PiggyPiglet
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package me.piggypiglet.framework.commands;

import com.google.inject.Inject;
import me.piggypiglet.framework.Framework;
import me.piggypiglet.framework.commands.implementations.HelpCommand;
import me.piggypiglet.framework.user.User;

import java.util.List;

public final class CommandHandler {
    @Inject private Framework framework;
    @Inject private HelpCommand defHelpCommand;

    private List<Command> commands;
    private Command helpCommand;

    /**
     * Find a matching command accessible to this command handler, and run it with the user's input
     * @param user User to run the command on
     * @param message String user input
     */
    public void process(User user, String message) {
        if (message.startsWith(framework.getCommandPrefix())) {
            message = message.replaceFirst(framework.getCommandPrefix(), "").trim();

            if (message.isEmpty()) {
                helpCommand.run(user, new String[]{});
                return;
            }

            for (Command c : commands) {
                String cmd = c.getCommand();

                if (startsWith(message, cmd)) {
                    List<String> permissions = c.getPermissions();

                    if (permissions.size() == 0 || permissions.stream().anyMatch(user::hasPermission)) {
                        String[] args = args(message.replaceFirst(cmd, "").trim());

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

    /**
     * Set this commandhandler's commands.
     * @param commands Commands to set
     */
    public void setCommands(List<Command> commands) {
        this.commands = commands;
        helpCommand = commands.stream().filter(Command::isDefault).findFirst().orElse(defHelpCommand);
    }

    /**
     * Get commands this command handler can process
     * @return List of commands
     */
    public List<Command> getCommands() {
        return commands;
    }

    private String[] args(String text) {
        String[] args = text.trim().split("\\s+(?=([^\"]*\"[^\"]*\")*[^\"]*$)");

        return args[0].isEmpty() ? new String[]{} : args;
    }

    private boolean startsWith(String msg, String q) {
        msg = msg.toLowerCase();
        q = q.toLowerCase();

        return msg.endsWith(q) || msg.startsWith(q + " ");
    }
}
