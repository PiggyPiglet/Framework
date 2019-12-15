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

package me.piggypiglet.framework.commands.framework;

import me.piggypiglet.framework.user.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Command<T extends User> {
    private final String prefix;

    private List<String> handlers = new ArrayList<>();
    private String description = "null";
    private String usage = "<required args> [optional args]";
    private List<String> permissions = new ArrayList<>();
    private boolean def = false;

    private String handler;

    /**
     * Configure options for this command.
     */
    protected Options options = new Options();

    protected Command(String prefix) {
        this.prefix = prefix;
    }

    /**
     *
     * @param user Wrapper for any sort of user interaction
     * @param args Message, excluding the prefix and command, split via space. Multiple word arguments can be achieved by using double quotes in the user's input. For example, test "multi word" would only be two arguments.
     * @return Whether the command's usage method should be sent or not. Returning false will send the message.
     */
    protected abstract boolean execute(T user, String[] args);

    public boolean run(T user, String[] args, String handler) {
        this.handler = handler;
        return execute(user, args);
    }

    public String getCommand() {
        return prefix;
    }

    public List<String> getHandlers() {
        return handlers;
    }

    public String getDescription() {
        return description;
    }

    public String getUsage() {
        return usage;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public boolean isDefault() {
        return def;
    }

    protected String getHandler() {
        return handler;
    }

    protected class Options {
        /**
         * Which handlers should be able to process this command? Leave empty for it to be runnable on all handlers.
         * @param handlers Handler references
         * @return Options
         */
        public Options handlers(String... handlers) {
            Command.this.handlers = Arrays.asList(handlers);
            return this;
        }

        /**
         * Description that will show for this command in the help command
         * @param description String describing the command, keep it short
         * @return Options
         */
        public Options description(String description) {
            Command.this.description = description;
            return this;
        }

        /**
         * Command's usage, should be self explanatory, but an example is &lt;required args&gt; [optional args].
         * @param usage String
         * @return Options
         */
        public Options usage(String usage) {
            Command.this.usage = usage;
            return this;
        }

        /**
         * What permissions will the User need to run this command? Leave empty for no permissions needed.
         * @param permissions Permissions
         * @return Options
         */
        public Options permissions(String... permissions) {
            Command.this.permissions = Arrays.asList(permissions);
            return this;
        }

        /**
         * Is this a default command? For example a help command.
         * @param value Boolean
         * @return Options
         */
        public Options def(boolean value) {
            def = value;
            return this;
        }
    }
}
