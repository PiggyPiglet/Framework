package me.piggypiglet.test.managers;

import com.google.inject.Singleton;
import me.piggypiglet.framework.mysql.manager.MySQLManager;
import me.piggypiglet.test.tables.PeopleTable;
import me.piggypiglet.test.tables.objects.Person;

import java.util.List;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
@Singleton
public final class PeopleManager extends MySQLManager<Person> {
    public PeopleManager() {
        super(new PeopleTable());
    }

    @Override
    protected void populate(List<Person> items) {}
}
