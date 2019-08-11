package me.piggypiglet.framework.hooks;

import com.google.inject.Singleton;
import me.piggypiglet.framework.registerables.ShutdownRegisterable;

import java.util.ArrayList;
import java.util.List;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
@Singleton
public final class ShutdownHook extends Thread {
    private final List<ShutdownRegisterable> registerables = new ArrayList<>();

    @Override
    public void run() {
        registerables.forEach(Runnable::run);
    }

    public List<ShutdownRegisterable> getRegisterables() {
        return registerables;
    }
}
