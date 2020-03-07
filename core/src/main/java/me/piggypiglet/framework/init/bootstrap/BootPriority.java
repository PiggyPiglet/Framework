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

package me.piggypiglet.framework.init.bootstrap;

import me.piggypiglet.framework.file.internal.registerables.FileRelocationRegisterable;
import me.piggypiglet.framework.file.internal.registerables.FileTypesRegisterable;
import me.piggypiglet.framework.file.internal.registerables.FilesRegisterable;
import me.piggypiglet.framework.file.internal.registerables.mapping.FileMappingRegisterable;
import me.piggypiglet.framework.file.internal.registerables.migration.MigrationRegisterable;
import me.piggypiglet.framework.language.internal.registerables.LanguageValuesRegisterable;
import me.piggypiglet.framework.logging.internal.registerables.LoggerRegistrarRegisterable;
import me.piggypiglet.framework.registerables.StartupRegisterable;
import me.piggypiglet.framework.registerables.startup.*;
import me.piggypiglet.framework.registerables.startup.commands.CommandHandlerRegisterable;
import me.piggypiglet.framework.registerables.startup.commands.CommandsRegisterable;
import me.piggypiglet.framework.scanning.internal.registerables.ScanningRequestFulfiller;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

/**
 * Boot priorities available to startup registerables, ran in the order as defined in here
 */
public enum BootPriority {
    BEFORE_IMPL,
    IMPL(ScanningRequestFulfiller.class, ImplementationFinderRegisterable.class,
            LoggerRegistrarRegisterable.class, FileTypesRegisterable.class,
            FilesRegisterable.class, FileRelocationRegisterable.class,
            MigrationRegisterable.class, FileMappingRegisterable.class,
            LanguageValuesRegisterable.class),
    AFTER_IMPL,
    BEFORE_MANUAL,
    MANUAL,
    AFTER_MANUAL,
    BEFORE_COMMANDS,
    COMMANDS(CommandsRegisterable.class, CommandHandlerRegisterable.class),
    AFTER_COMMANDS,
    BEFORE_ADDONS,
    ADDONS,
    AFTER_ADDONS,
    BEFORE_SHUTDOWN,
    SHUTDOWN(ManagersRegisterable.class, ShutdownRegisterablesRegisterable.class, ShutdownHookRegisterable.class),
    AFTER_SHUTDOWN(SyncRegisterable.class);

    private final List<Class<? extends StartupRegisterable>> def;

    @SafeVarargs
    BootPriority(@NotNull final Class<? extends StartupRegisterable>... def) {
        this.def = Arrays.asList(def);
    }

    @NotNull
    List<Class<? extends StartupRegisterable>> getDefault() {
        return def;
    }
}
