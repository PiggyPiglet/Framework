package me.piggypiglet.framework.registerables;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public abstract class ShutdownRegisterable implements Runnable {
    protected abstract void execute();

    @Override
    public void run() {
        execute();
    }
}