package me.piggypiglet.framework.jda.startup;

import com.google.inject.Inject;
import me.piggypiglet.framework.registerables.StartupRegisterable;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.reflections.Reflections;

import java.util.EventListener;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class EventsRegisterable extends StartupRegisterable {
    @Inject private JDABuilder jdaBuilder;
    @Inject private Reflections reflections;

    @Override
    protected void execute() {
        Stream.of(
                EventListener.class,
                ListenerAdapter.class
        ).map(this::getListeners).forEach(l -> l.forEach(jdaBuilder::addEventListeners));
    }

    private <T> List<T> getListeners(Class<T> clazz) {
        return reflections.getSubTypesOf(clazz).stream().map(injector::getInstance).collect(Collectors.toList());
    }
}
