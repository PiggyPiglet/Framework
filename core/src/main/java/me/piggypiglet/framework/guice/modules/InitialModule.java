package me.piggypiglet.framework.guice.modules;

import com.google.inject.*;
import me.piggypiglet.framework.Framework;
import org.reflections.Reflections;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class InitialModule extends AbstractModule {
    private final Framework config;

    public InitialModule(Framework config) {
        this.config = config;
    }

    public Injector createInjector() {
        return Guice.createInjector(this);
    }

    @Provides
    @Singleton
    public Framework providesConfig() {
        return config;
    }

    @Provides
    @Singleton
    public Reflections providesReflections() {
        return new Reflections(config.getPckg());
    }
}
