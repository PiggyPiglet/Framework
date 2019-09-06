package me.piggypiglet.framework.velocity.registerables;

import com.google.inject.Inject;
import com.velocitypowered.api.event.EventHandler;
import com.velocitypowered.api.event.EventManager;
import com.velocitypowered.api.event.Subscribe;
import me.piggypiglet.framework.guice.objects.MainBinding;
import me.piggypiglet.framework.reflection.Reflections;
import me.piggypiglet.framework.registerables.StartupRegisterable;
import me.piggypiglet.framework.utils.ReflectionUtils;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class EventFinderRegisterable extends StartupRegisterable {
    @Inject private Reflections reflections;
    @Inject private EventManager eventManager;
    @Inject private MainBinding main;

    @Override
    @SuppressWarnings("unchecked")
    protected void execute() {
        final Object main = this.main.getInstance();
        reflections.getClassesWithAnnotatedMethods(Subscribe.class).stream().map(injector::getInstance).filter(o -> o != main).forEach(l -> eventManager.register(main, l));
        reflections.getSubTypesOf(EventHandler.class).forEach(l -> eventManager.register(main, ReflectionUtils.getClassGeneric(l), injector.getInstance(l)));
    }
}
