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

package me.piggypiglet.framework.mysql.registerables.startup;

import co.aikar.idb.DB;
import co.aikar.idb.DatabaseOptions;
import co.aikar.idb.PooledDatabaseOptions;
import com.google.inject.Inject;
import me.piggypiglet.framework.file.framework.FileConfiguration;
import me.piggypiglet.framework.file.objects.FileWrapper;
import me.piggypiglet.framework.logging.LoggerFactory;
import me.piggypiglet.framework.mysql.annotations.SQL;
import me.piggypiglet.framework.mysql.annotations.SQLConfig;
import me.piggypiglet.framework.mysql.utils.MySQLUtils;
import me.piggypiglet.framework.registerables.StartupRegisterable;
import me.piggypiglet.framework.task.Task;

public final class MySQLRegisterable extends StartupRegisterable {
    @Inject private Task task;
    @Inject @SQLConfig private FileConfiguration config;
    @Inject @SQL private FileWrapper sql;

    @Override
    protected void execute() {
        LoggerFactory.getLogger("MySQL").info("Initializing MySQL module.");

        final String sql = this.sql.getFileContent();

        task.async(r -> {
            final String[] tables = config.getStringList("tables").toArray(new String[]{});
            final String[] schemas = sql.split("-");

            final DatabaseOptions options = DatabaseOptions.builder().mysql(
                    config.getString("user"),
                    config.getString("password"),
                    config.getString("db"),
                    config.getString("host")
            ).build();

            DB.setGlobalDatabase(PooledDatabaseOptions.builder().options(options).createHikariDatabase());

            for (int i = 0; i < tables.length; i++) {
                try {
                    if (DB.getFirstRow("SHOW TABLES LIKE '" + tables[i] + "'") == null) {
                        DB.executeInsert(schemas[i]);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            MySQLUtils.isReady().complete(true);
        });
    }
}