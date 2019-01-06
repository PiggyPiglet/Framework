package me.piggypiglet.framework.dependencies.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

// ------------------------------
// Copyright (c) PiggyPiglet 2018
// https://www.piggypiglet.me
// ------------------------------
public final class BinderModule extends AbstractModule {
    private final Class main;
    private final Class[] staticInjections;

    public BinderModule(Class main, Class... staticInjections) {
        this.main = main;
        this.staticInjections = staticInjections;
    }

    public Injector createInjector() {
        return Guice.createInjector(this);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void configure() {
        bind(main).toInstance(main);
        requestStaticInjection(staticInjections);
    }
}
