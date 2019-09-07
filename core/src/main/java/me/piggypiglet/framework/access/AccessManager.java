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

package me.piggypiglet.framework.access;

import com.google.inject.Inject;
import com.google.inject.Injector;
import me.piggypiglet.framework.commands.CommandHandlers;
import me.piggypiglet.framework.user.User;

public final class AccessManager {
    @Inject private CommandHandlers commandHandlers;

    /**
     * Find and run a command in a specific handler.
     * @param commandHandler Handler to attempt to find the command in
     * @param user User running the command
     * @param message Message, including command prefix, command, and arguments.
     */
    public void processCommand(String commandHandler, User user, String message) {
        commandHandlers.process(commandHandler, user, message);
    }

    /**
     * Create a new command handler.
     * @param name String the command handler will be referenced by
     * @param injector Instance of the applications injector.
     */
    public void newHandler(String name, Injector injector) {
        commandHandlers.newHandler(name, injector);
    }
}
