package me.piggypiglet.framework.jda;

import me.piggypiglet.framework.jda.annotation.Bot;
import me.piggypiglet.framework.jda.shutdown.JDAShutdown;
import me.piggypiglet.framework.jda.startup.BuilderRegisterable;
import me.piggypiglet.framework.jda.startup.EventsRegisterable;
import me.piggypiglet.framework.jda.startup.FinishRegisterable;
import me.piggypiglet.framework.utils.annotations.addon.Addon;
import me.piggypiglet.framework.utils.annotations.addon.File;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
@Addon(
        startup = {BuilderRegisterable.class, EventsRegisterable.class, FinishRegisterable.class},
        files = {@File(
                config = true,
                name = "bot",
                externalPath = "./bot.json",
                internalPath = "/bot.json",
                annotation = Bot.class
        )},
        shutdown = JDAShutdown.class
)
public final class JDAAddon {
}
