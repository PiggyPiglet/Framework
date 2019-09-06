package me.piggypiglet.test;

import me.piggypiglet.framework.jars.loading.framework.Jar;
import me.piggypiglet.framework.jars.loading.framework.Loader;
import me.piggypiglet.test.framework.jars.framework.Module;

import java.io.File;
import java.util.Arrays;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class TestLoader extends Loader<Module> {
    public TestLoader() {
        super("modules/", r -> Arrays.asList(r.getInterfaces()).contains(Module.class));
    }

    @Override
    public Jar[] process(File[] files) {
        return new Jar[] {
                new Jar() {
                    @Override
                    public String getName() {
                        return "test";
                    }

                    @Override
                    public String getPath() {
                        return "modules/testr.jar";
                    }

                    @Override
                    public String getVersion() {
                        return "1.0.0";
                    }
                }
        };
    }

    @Override
    protected void init(Module module) {
        module.start();
    }
}
