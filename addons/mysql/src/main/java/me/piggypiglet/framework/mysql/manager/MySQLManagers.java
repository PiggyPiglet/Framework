package me.piggypiglet.framework.mysql.manager;

import com.google.inject.Inject;
import me.piggypiglet.framework.managers.ManagersManager;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class MySQLManagers {
    @Inject private ManagersManager managersManager;

    @SuppressWarnings("unchecked")
    public void saveAll() {
        managersManager.getManagers()
                .stream()
                .filter(m -> m instanceof MySQLManager).map(m -> (MySQLManager) m)
                .forEach(m -> m.getAll().forEach(m.getTable()::save));
    }
}
