package me.piggypiglet.framework.jda.startup;

import com.google.inject.Inject;
import me.piggypiglet.framework.registerables.StartupRegisterable;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class FinishRegisterable extends StartupRegisterable {
    @Inject private JDABuilder jdaBuilder;

    @Override
    protected void execute() {
        try {
            addBinding(JDA.class, jdaBuilder.build());
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
}
