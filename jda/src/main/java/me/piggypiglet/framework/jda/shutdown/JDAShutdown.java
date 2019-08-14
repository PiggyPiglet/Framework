package me.piggypiglet.framework.jda.shutdown;

import com.google.inject.Inject;
import me.piggypiglet.framework.registerables.ShutdownRegisterable;
import net.dv8tion.jda.api.JDA;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class JDAShutdown extends ShutdownRegisterable {
    @Inject private JDA jda;

    @Override
    protected void execute() {
        jda.shutdownNow();
    }
}
