package me.piggypiglet.framework.task;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public abstract class GRunnable implements Runnable {
    /**
     * Util method to put thread to sleep without having to manually catch an exception; code shortener
     * @param ms Milliseconds to sleep for
     */
    protected void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}