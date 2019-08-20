package me.piggypiglet.test;

import com.google.inject.Singleton;
import me.piggypiglet.framework.mysql.manager.MySQLManager;

import java.util.List;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
@Singleton
public final class PeopleHandler extends MySQLManager<Person> {
    public PeopleHandler() {
        super(new PeopleTable());
    }

    @Override
    protected void populate(List<Person> items) {}
}
