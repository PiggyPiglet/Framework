package me.piggypiglet.framework.guice.modules;

import com.google.inject.*;
import me.piggypiglet.framework.Framework;
import me.piggypiglet.framework.bootstrap.FrameworkBootstrap;
import me.piggypiglet.framework.guice.objects.MainBinding;
import me.piggypiglet.framework.reflection.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class InitialModule extends AbstractModule {
    private final FrameworkBootstrap main;
    private final Framework config;

    public InitialModule(FrameworkBootstrap main, Framework config) {
        this.main = main;
        this.config = config;
    }

    public Injector createInjector() {
        return Guice.createInjector(this);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void configure() {
        final MainBinding main = config.getMain();

        if (main.getAnnotation() != null) {
            bind(main.getClazz()).annotatedWith(main.getAnnotation()).toInstance(main.getInstance());
        } else {
            bind(main.getClazz()).toInstance(main.getInstance());
        }
    }

    @Provides
    @Singleton
    public FrameworkBootstrap providesFrameworkBootstrap() {
        return main;
    }

    @Provides
    @Singleton
    public Framework providesConfig() {
        return config;
    }

    @Provides
    @Singleton
    public Reflections providesReflections() {
        return new Reflections(new org.reflections.Reflections(config.getPckg(), new MethodAnnotationsScanner(), new TypeAnnotationsScanner(), new SubTypesScanner()));
    }
}
