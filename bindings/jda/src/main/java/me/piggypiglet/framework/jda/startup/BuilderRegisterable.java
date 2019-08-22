package me.piggypiglet.framework.jda.startup;

import com.google.inject.Inject;
import me.piggypiglet.framework.file.framework.FileConfiguration;
import me.piggypiglet.framework.jda.annotation.Bot;
import me.piggypiglet.framework.logging.LoggerFactory;
import me.piggypiglet.framework.registerables.StartupRegisterable;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class BuilderRegisterable extends StartupRegisterable {
    @Inject @Bot private FileConfiguration config;

    @Override
    protected void execute() {
        LoggerFactory.getLogger("JDA").info("Initializing bot and bindings.");

        addBinding(JDABuilder.class, new JDABuilder(AccountType.BOT)
                .setToken(config.getString("token"))
                .setActivity(Activity.of(
                        Activity.ActivityType.valueOf(config.getString("activity.type", "default").toUpperCase()), config.getString("activity.activity")
                ))
        );
    }
}
