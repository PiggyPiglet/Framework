package me.piggypiglet.framework.mysql.registerables.shutdown;

import co.aikar.idb.DB;
import me.piggypiglet.framework.registerables.ShutdownRegisterable;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class MySQLShutdown extends ShutdownRegisterable {
    @Override
    protected void execute() {
        DB.close();
    }
}
