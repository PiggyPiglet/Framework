/*
 * MIT License
 *
 * Copyright (c) 2019-2020 PiggyPiglet
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
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Command<U extends User, O extends Command<U, O>.Options<O>> {
    private final String prefix;

    private List<String> handlers = new ArrayList<>();
    private String description = "null";
    private String usage = "<required args> [optional args]";
    private List<String> permissions = new ArrayList<>();
    private boolean def = false;

    private String handler;

    protected Command(@NotNull final String prefix) {
        this.prefix = prefix;
    }

    /**
     *
     * @param user Wrapper for any sort of user interaction
     * @param args Message, excluding the prefix and command, split via space. Multiple word arguments can be achieved by using double quotes in the user's input. For example, test "multi word" would only be two arguments.
     * @return Whether the command's usage method should be sent or not. Returning false will send the message.
     */
    protected abstract boolean execute(@NotNull final U user, @NotNull final String[] args);

    public boolean run(@NotNull final U user, @NotNull final String[] args,
                       @NotNull final String handler) {
        this.handler = handler;
        return execute(user, args);
    }

    @NotNull
    protected abstract Options<O> options();

    @NotNull
    public String getCommand() {
        return prefix;
    }

    @NotNull
    public List<String> getHandlers() {
        return handlers;
    }

    @NotNull
    public String getDescription() {
        return description;
    }

    @NotNull
    public String getUsage() {
        return usage;
    }

    @NotNull
    public List<String> getPermissions() {
        return permissions;
    }

    public boolean isDefault() {
        return def;
    }

    @NotNull
    protected String getHandler() {
        return handler;
    }

    protected abstract class Options<R extends Options<R>> {
        @SuppressWarnings("unchecked") protected final R instance = (R) this;

        /**
         * Which handlers should be able to process this command? Leave empty for it to be runnable on all handlers.
         * @param handlers Handler references
         * @return Options
         */
        @NotNull
        public R handlers(@NotNull final String... handlers) {
            Command.this.handlers = Arrays.asList(handlers);
            return instance;
        }

        /**
         * Description that will show for this command in the help command
         * @param description String describing the command, keep it short
         * @return Options
         */
        @NotNull
        public R description(@NotNull final String description) {
            Command.this.description = description;
            return instance;
        }

        /**
         * Command's usage, should be self explanatory, but an example is &lt;required args&gt; [optional args].
         * @param usage String
         * @return Options
         */
        @NotNull
        public R usage(@NotNull final String usage) {
            Command.this.usage = usage;
            return instance;
        }

        /**
         * What permissions will the User need to run this command? Leave empty for no permissions needed.
         * @param permissions Permissions
         * @return Options
         */
        @NotNull
        public R permissions(@NotNull final String... permissions) {
            Command.this.permissions = Arrays.asList(permissions);
            return instance;
        }

        /**
         * Is this a default command? For example a help command.
         * @param value Boolean
         * @return Options
         */
        @NotNull
        public R def(final boolean value) {
            def = value;
            return instance;
        }
    }
}
