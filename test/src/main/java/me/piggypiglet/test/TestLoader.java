package me.piggypiglet.test;

import me.piggypiglet.framework.jars.loading.Jar;
import me.piggypiglet.framework.jars.loading.Loader;

import java.net.URL;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class TestLoader extends Loader<TestLoader.Test> {
    public TestLoader() {
        super(Test.class);
    }

    @Override
    protected Jar[] loadAll(String dir, Test[] datas) {
        return datas;
    }

    public static final class Test implements Jar {
        private final String name;
        private final String version;
        private final String hash;
        private final URL url;

        public Test(String name, String version, String hash, URL url) {
            this.name = name;
            this.version = version;
            this.hash = hash;
            this.url = url;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String getVersion() {
            return version;
        }

        @Override
        public String getHash() {
            return hash;
        }

        @Override
        public URL getUrl() {
            return url;
        }
    }
}
