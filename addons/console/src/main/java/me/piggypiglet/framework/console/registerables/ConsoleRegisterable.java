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

package me.piggypiglet.framework.console.registerables;

import com.google.inject.Inject;
import me.piggypiglet.framework.commands.CommandHandlers;
import me.piggypiglet.framework.console.user.ConsoleUser;
import me.piggypiglet.framework.logging.LoggerFactory;
import me.piggypiglet.framework.logging.annotations.LoggerName;
import me.piggypiglet.framework.logging.framework.Logger;
import me.piggypiglet.framework.registerables.StartupRegisterable;
import me.piggypiglet.framework.task.Task;
import me.piggypiglet.framework.user.User;

import java.util.Scanner;

public final class ConsoleRegisterable extends StartupRegisterable {
    @Inject private Task task;
    @Inject private CommandHandlers commandHandlers;

    private final User user;

    @Inject
    public ConsoleRegisterable(@LoggerName("CONSOLE") Logger<?> logger) {
        user = new ConsoleUser(logger);
    }

    @Override
    protected void execute() {
        LoggerFactory.getLogger("Console").debug("Initializing console module.");

        commandHandlers.newHandler("console", injector);

        task.async(r -> {
            Scanner input = new Scanner(System.in);

            //noinspection InfiniteLoopStatement
            while (true) {
                final String str = input.nextLine();
                commandHandlers.process("console", user, str);
            }
        });
    }
}
