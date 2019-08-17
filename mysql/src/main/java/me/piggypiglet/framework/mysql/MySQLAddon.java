package me.piggypiglet.framework.mysql;

import me.piggypiglet.framework.mysql.annotations.SQL;
import me.piggypiglet.framework.mysql.annotations.SQLConfig;
import me.piggypiglet.framework.mysql.registerables.shutdown.MySQLShutdown;
import me.piggypiglet.framework.mysql.registerables.startup.MySQLRegisterable;
import me.piggypiglet.framework.mysql.registerables.startup.MySQLSavingRegisterable;
import me.piggypiglet.framework.utils.annotations.addon.Addon;
import me.piggypiglet.framework.utils.annotations.addon.File;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
@Addon(
        startup = {MySQLRegisterable.class, MySQLSavingRegisterable.class},
        shutdown = MySQLShutdown.class,
        files = {@File(
                config = true,
                name = "sql-config",
                externalPath = "./mysql.json",
                internalPath = "/mysql.json",
                annotation = SQLConfig.class
        ), @File(
                config = false,
                name = "sql",
                externalPath = "./schema.sql",
                internalPath = "/schema.sql",
                annotation = SQL.class
        )}
)
public final class MySQLAddon {
}
