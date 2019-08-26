package me.piggypiglet.test;

import me.piggypiglet.framework.logging.LoggerFactory;
import me.piggypiglet.framework.registerables.StartupRegisterable;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class HelloRegisterable extends StartupRegisterable {
    @Override
    protected void execute() {
        LoggerFactory.getLogger("Hello").info("Congratz, you cracked it. Enter !stop to stop the program.");
    }
}
