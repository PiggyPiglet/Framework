package me.piggypiglet.framework.velocity.registerables;

import com.google.inject.Inject;
import com.velocitypowered.api.event.EventHandler;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.proxy.ProxyServer;
import me.piggypiglet.framework.reflection.Reflections;
import me.piggypiglet.framework.registerables.StartupRegisterable;
import me.piggypiglet.framework.utils.ReflectionUtils;
import me.piggypiglet.framework.utils.annotations.Main;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class EventFinderRegisterable extends StartupRegisterable {
    @Inject private Reflections reflections;
    @Inject private ProxyServer server;
    @Inject @Main private Object main;

    @Override
    @SuppressWarnings("unchecked")
    protected void execute() {
        reflections.getClassesWithAnnotatedMethods(Subscribe.class).stream().map(injector::getInstance).forEach(l -> server.getEventManager().register(main, l));
        reflections.getSubTypesOf(EventHandler.class).forEach(l -> server.getEventManager().register(main, ReflectionUtils.getClassGeneric(l), injector.getInstance(l)));
    }
}
