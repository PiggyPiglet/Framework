package me.piggypiglet.framework.sponge.registerables;

import com.google.inject.Inject;
import me.piggypiglet.framework.reflection.Reflections;
import me.piggypiglet.framework.registerables.StartupRegisterable;
import me.piggypiglet.framework.utils.ReflectionUtils;
import me.piggypiglet.framework.utils.annotations.Main;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.EventListener;
import org.spongepowered.api.event.Listener;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class EventFinderRegisterable extends StartupRegisterable {
    @Inject private Reflections reflections;
    @Inject @Main private Object main;

    @Override
    @SuppressWarnings("unchecked")
    protected void execute() {
        reflections.getClassesWithAnnotatedMethods(Listener.class).stream().map(injector::getInstance).filter(o -> o != main).forEach(l -> Sponge.getEventManager().registerListeners(main, l));
        reflections.getSubTypesOf(EventListener.class).forEach(l -> Sponge.getEventManager().registerListener(main, ReflectionUtils.getClassGeneric(l), injector.getInstance(l)));
    }
}