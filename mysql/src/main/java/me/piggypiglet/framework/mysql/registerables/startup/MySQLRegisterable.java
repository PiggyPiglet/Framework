package me.piggypiglet.framework.mysql.registerables.startup;

import co.aikar.idb.DB;
import co.aikar.idb.DatabaseOptions;
import co.aikar.idb.PooledDatabaseOptions;
import com.google.inject.Inject;
import me.piggypiglet.framework.file.framework.FileConfiguration;
import me.piggypiglet.framework.file.objects.FileWrapper;
import me.piggypiglet.framework.mysql.annotations.SQL;
import me.piggypiglet.framework.mysql.annotations.SQLConfig;
import me.piggypiglet.framework.mysql.utils.MySQLUtils;
import me.piggypiglet.framework.registerables.StartupRegisterable;
import me.piggypiglet.framework.task.Task;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class MySQLRegisterable extends StartupRegisterable {
    @Inject private Task task;
    @Inject @SQLConfig private FileConfiguration config;
    @Inject @SQL private FileWrapper sql;

    @Override
    protected void execute() {
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