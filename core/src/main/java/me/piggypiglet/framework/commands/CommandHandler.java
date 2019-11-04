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
import me.piggypiglet.framework.utils.annotations.reflection.Default;

import java.util.List;

import static me.piggypiglet.framework.lang.Lang.Values.*;

public class CommandHandler {
    @Inject private Framework framework;
    @Inject private HelpCommand defHelpCommand;

    private List<Command> commands;
    private Command helpCommand;

    public void handle(User user, String message) {
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

                        if (!run(user, c)) return;

                        if (!c.run(user, args)) {
                            user.sendMessage(INCORRECT_USAGE, cmd, c.getUsage());
                        }
                    } else {
                        user.sendMessage(NO_PERMISSION);
                    }

                    return;
                }
            }

            user.sendMessage(UNKNOWN_COMMAND);
        }
    }

    protected boolean run(User user, Command command) {
        return process(user, command);
    }

    /**
     * Handle any extra processing the default method doesn't provide in sub classes. Custom messages will need to be handled here.
     * @param user User
     * @param command Command
     * @return boolean on whether to execute or not
     */
    protected boolean process(User user, Command command) {
        return true;
    }

    /**
     * Set this commandhandler's commands.
     * @param commands Commands to set
     */
    public void setCommands(List<Command> commands) {
        this.commands = commands;
        helpCommand = commands.stream().filter(c -> !(c.getClass().isAnnotationPresent(Default.class))).filter(Command::isDefault).findFirst().orElse(defHelpCommand);
    }

    /**
     * Get commands this command handler can process
     * @return List of commands
     */
    public List<Command> getCommands() {
        return commands;
    }

    private String[] args(String text) {
        String[] args = CommandHandlers.ARGUMENT_PATTERN.split(text.trim());

        return args[0].isEmpty() ? new String[]{} : args;
    }

    private boolean startsWith(String msg, String q) {
        msg = msg.toLowerCase();
        q = q.toLowerCase();

        return msg.endsWith(q) || msg.startsWith(q + " ");
    }
}
