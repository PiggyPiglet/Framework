package me.piggypiglet.test.commands;

import com.google.inject.Inject;
import me.piggypiglet.framework.commands.Command;
import me.piggypiglet.framework.user.User;
import me.piggypiglet.test.managers.PeopleManager;
import me.piggypiglet.test.tables.objects.Person;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class AddPerson extends Command {
    @Inject private PeopleManager peopleManager;

    public AddPerson() {
        super("person add");
        options.usage("<name> <age>");
    }

    @Override
    protected boolean execute(User user, String[] args) {
        if (args.length >= 2) {
            peopleManager.add(new Person(args[0], Integer.parseInt(args[1])));
            user.sendMessage("Successfully added a new person to the PeopleManager with the name %s, and an age of %s.", args[0], args[1]);
            return true;
        }

        return false;
    }
}
