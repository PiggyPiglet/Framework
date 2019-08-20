package me.piggypiglet.test;

import com.google.inject.Inject;
import me.piggypiglet.framework.commands.Command;
import me.piggypiglet.framework.user.User;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class SearchCommand extends Command {
    @Inject private PeopleHandler peopleHandler;

    public SearchCommand() {
        super("search");
        options.usage("<name>");
    }

    @Override
    protected boolean execute(User user, String[] args) {
        if (args.length > 0) {
            user.sendMessage(String.valueOf(peopleHandler.search(args[0])));
            return true;
        }

        return false;
    }
}
