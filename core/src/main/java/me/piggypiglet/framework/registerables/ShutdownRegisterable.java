package me.piggypiglet.framework.registerables;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public abstract class ShutdownRegisterable implements Runnable {
    /**
     * Code to be ran during registerable execution
     */
    protected abstract void execute();

    /**
     * Run the registerable
     */
    @Override
    public void run() {
        execute();
    }
}