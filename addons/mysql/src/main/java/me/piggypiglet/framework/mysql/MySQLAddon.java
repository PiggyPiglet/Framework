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

package me.piggypiglet.framework.mysql;

import me.piggypiglet.framework.addon.framework.Addon;
import me.piggypiglet.framework.addon.init.AddonBuilder;
import me.piggypiglet.framework.addon.init.AddonData;
import me.piggypiglet.framework.mysql.annotations.SQL;
import me.piggypiglet.framework.mysql.annotations.SQLConfig;
import me.piggypiglet.framework.mysql.registerables.shutdown.MySQLFinalSave;
import me.piggypiglet.framework.mysql.registerables.shutdown.MySQLShutdown;
import me.piggypiglet.framework.mysql.registerables.startup.MySQLRegisterable;
import me.piggypiglet.framework.mysql.registerables.startup.MySQLSavingRegisterable;
import org.jetbrains.annotations.NotNull;

public final class MySQLAddon extends Addon {
    @NotNull
    @Override
    protected AddonData provideConfig(@NotNull final AddonBuilder<AddonData> builder) {
        return builder
                .startup(MySQLRegisterable.class, MySQLSavingRegisterable.class)
                .shutdown(MySQLFinalSave.class, MySQLShutdown.class)
                .files()
                        .config("sql-config", "mysql.json", "mysql.json", SQLConfig.class)
                        .file("sql", "schema.sql", "schema.sql", SQL.class)
                        .build()
                .build();
    }
}
