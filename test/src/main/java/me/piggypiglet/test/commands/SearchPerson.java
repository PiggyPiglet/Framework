package me.piggypiglet.test.commands;

import com.google.inject.Inject;
import me.piggypiglet.framework.commands.Command;
import me.piggypiglet.framework.user.User;
import me.piggypiglet.test.managers.PeopleManager;
import me.piggypiglet.test.tables.objects.Person;

import java.util.List;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class SearchPerson extends Command {
    @Inject private PeopleManager peopleManager;

    public SearchPerson() {
        super("person search");
        options.usage("<name>");
    }

    @Override
    protected boolean execute(User user, String[] args) {
        if (args.length > 0) {
            List<Person> people = peopleManager.search(args[0]);

            if (people.size() > 0) {
                final Person person = people.get(0);
                user.sendMessage("I've found a person from your query, their name is %s and their age is %s.", person.getTitle(), person.getAge());
            } else {
                user.sendMessage("No people found in the database.");
            }

            return true;
        }

        return false;
    }
}
