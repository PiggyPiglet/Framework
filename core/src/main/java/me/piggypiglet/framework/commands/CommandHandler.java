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
import me.piggypiglet.framework.commands.framework.Command;
import me.piggypiglet.framework.commands.implementations.HelpCommand;
import me.piggypiglet.framework.user.User;
import me.piggypiglet.framework.utils.annotations.reflection.def.Default;
import me.piggypiglet.framework.utils.annotations.reflection.def.UltraDefault;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static me.piggypiglet.framework.lang.Lang.Values.*;

public class CommandHandler {
    public static final Pattern ARGUMENT_PATTERN = Pattern.compile("\\s+(?=([^\"]*\"[^\"]*\")*[^\"]*$)");

    private static final BiFunction<Command, Class<? extends Annotation>, Boolean> HAS_ANNOTATION = (c, a) -> c.getClass().isAnnotationPresent(a);
    private static final Predicate<Command> IS_ULTRA_DEFAULT = c -> HAS_ANNOTATION.apply(c, UltraDefault.class);
    private static final Predicate<Command> IS_DEFAULT = c -> HAS_ANNOTATION.apply(c, Default.class);

    @Inject private Framework framework;
    @Inject private HelpCommand defHelpCommand;

    private Set<Command> commands;
    private Command helpCommand;

    public void handle(User user, String message, String handler) {
        if (message.startsWith(framework.getCommandPrefix())) {
            message = message.replaceFirst(framework.getCommandPrefix(), "").trim();

            if (message.isEmpty()) {
                helpCommand.run(user, new String[]{}, handler);
                return;
            }

            for (Command c : commands) {
                String cmd = c.getCommand();

                if (startsWith(message, cmd)) {
                    List<String> permissions = c.getPermissions();

                    if (permissions.size() == 0 || permissions.stream().anyMatch(user::hasPermission)) {
                        String[] args = args(message.replaceFirst(cmd, "").trim());

                        if (!run(user, c)) return;

                        if (!c.run(user, args, handler)) {
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
    public void setCommands(Set<Command> commands) {
        Set<Command> clone = new HashSet<>(commands);

        Set<Command> defs = clone.stream()
                .filter(Command::isDefault)
                .filter(c -> !IS_ULTRA_DEFAULT.test(c))
                .collect(Collectors.toSet());

        if (defs.size() > 1 && !defs.stream().allMatch(IS_DEFAULT)) {
            defs = defs.stream().filter(c -> !IS_DEFAULT.test(c)).collect(Collectors.toSet());
        }

        helpCommand = defs.stream().findFirst().orElse(defHelpCommand);

        if (!helpCommand.equals(defHelpCommand)) {
            clone = clone.stream().filter(c -> !IS_ULTRA_DEFAULT.test(c)).filter(c -> !IS_DEFAULT.test(c)).collect(Collectors.toSet());
            clone.add(helpCommand);
        }

        this.commands = clone;
    }

    /**
     * Get commands this command handler can process
     * @return List of commands
     */
    public Set<Command> getCommands() {
        return commands;
    }

    private String[] args(String text) {
        String[] args = ARGUMENT_PATTERN.split(text.trim());

        return args[0].isEmpty() ? new String[]{} : args;
    }

    private boolean startsWith(String msg, String q) {
        msg = msg.toLowerCase();
        q = q.toLowerCase();

        return msg.endsWith(q) || msg.startsWith(q + " ");
    }
}
