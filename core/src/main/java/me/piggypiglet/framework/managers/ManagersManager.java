package me.piggypiglet.framework.managers;

import com.google.inject.Singleton;

import java.util.ArrayList;
import java.util.List;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
@Singleton
public final class ManagersManager {
    private final List<Manager> managers = new ArrayList<>();

    /**
     * Run the setup method on every manager stored by this class
     */
    public void setup() {
        managers.forEach(Manager::setup);
    }

    /**
     * Get all managers stored in this singleton
     * @return List of managers
     */
    public List<Manager> getManagers() {
        return managers;
    }
}
