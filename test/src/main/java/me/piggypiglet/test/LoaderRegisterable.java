package me.piggypiglet.test;

import com.google.inject.Inject;
import me.piggypiglet.framework.jars.loading.JarLoader;
import me.piggypiglet.framework.registerables.StartupRegisterable;

import java.net.MalformedURLException;
import java.net.URL;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class LoaderRegisterable extends StartupRegisterable {
    @Inject private JarLoader jarLoader;

    @Override
    protected void execute() {
        try {
            jarLoader.loadAll("modules/", new TestLoader.Test("testr", "1.0.0", "05c77d015d5e69ee406d0b3b553f962e", new URL("https://rpf.piggypiglet.me/test/testr-1.0.0.jar")));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
