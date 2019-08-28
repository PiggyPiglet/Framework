package me.piggypiglet.demo.commands;

import com.google.inject.Inject;
import me.piggypiglet.framework.commands.Command;
import me.piggypiglet.framework.file.framework.FileConfiguration;
import me.piggypiglet.framework.user.User;
import me.piggypiglet.framework.utils.annotations.files.Config;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class DemoInfoCommand extends Command {
    @Inject @Config private FileConfiguration config;

    public DemoInfoCommand() {
        super("info");
        options.usage("").description("Get info about the demo plugin.");
    }

    @Override
    protected boolean execute(User user, String[] args) {
        String[] message = {
                "This is a demo plugin, using the RPF Bukkit Bindings. Here's the info stored in your config:",
                "Broadcast Message: %s",
                "Broadcast Interval: %s"
        };

        user.sendMessage(String.join("\n", message), config.getString("broadcast.message"), config.getString("broadcast.interval"));
        return true;
    }
}