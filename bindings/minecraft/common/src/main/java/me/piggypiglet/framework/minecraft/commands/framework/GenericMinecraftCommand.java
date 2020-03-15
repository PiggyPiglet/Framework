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

package me.piggypiglet.framework.minecraft.commands.framework;

import me.piggypiglet.framework.commands.framework.Command;
import me.piggypiglet.framework.minecraft.user.MinecraftUser;
import org.jetbrains.annotations.NotNull;

public abstract class GenericMinecraftCommand<U extends MinecraftUser, O extends GenericMinecraftCommand<U, O>.Options<O>> extends Command<U, O> {
    private boolean playerOnly = false;
    private boolean consoleOnly = false;

    protected GenericMinecraftCommand(@NotNull final String command) {
        super(command);
    }

    public boolean isPlayerOnly() {
        return playerOnly;
    }

    public boolean isConsoleOnly() {
        return consoleOnly;
    }

    public abstract class Options<R extends Options<R>> extends Command<U, O>.Options<R> {
        @NotNull
        public R playerOnly(final boolean value) {
            GenericMinecraftCommand.this.playerOnly = value;
            return instance;
        }

        @NotNull
        public R consoleOnly(final boolean value) {
            GenericMinecraftCommand.this.consoleOnly = value;
            return instance;
        }
    }
}
