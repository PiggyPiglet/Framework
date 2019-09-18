package me.piggypiglet.framework.mysql.registerables.shutdown;

import com.google.inject.Inject;
import me.piggypiglet.framework.mysql.manager.MySQLManagers;
import me.piggypiglet.framework.registerables.ShutdownRegisterable;

public final class MySQLFinalSave extends ShutdownRegisterable {
    @Inject private MySQLManagers mySQLManagers;

    @Override
    protected void execute() {
        mySQLManagers.saveAll();
    }
}
