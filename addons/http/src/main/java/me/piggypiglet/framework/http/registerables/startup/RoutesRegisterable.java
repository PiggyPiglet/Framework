package me.piggypiglet.framework.http.registerables.startup;

import com.google.inject.Inject;
import me.piggypiglet.framework.http.responses.ResponseHandler;
import me.piggypiglet.framework.http.responses.Route;
import me.piggypiglet.framework.reflection.Reflections;
import me.piggypiglet.framework.registerables.StartupRegisterable;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class RoutesRegisterable extends StartupRegisterable {
    @Inject private Reflections reflections;
    @Inject private ResponseHandler responseHandler;

    @Override
    protected void execute() {
        reflections.getSubTypesOf(Route.class).stream().map(injector::getInstance).forEach(responseHandler.getRoutes()::add);
    }
}
