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

    public void setup() {
        managers.forEach(Manager::setup);
    }

    public List<Manager> getManagers() {
        return managers;
    }
}
