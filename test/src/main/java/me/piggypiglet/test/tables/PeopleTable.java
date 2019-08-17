package me.piggypiglet.test.tables;

import co.aikar.idb.DbRow;
import me.piggypiglet.framework.mysql.components.row.objects.KeyValueSet;
import me.piggypiglet.framework.mysql.table.Table;
import me.piggypiglet.test.tables.objects.Person;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class PeopleTable extends Table<Person> {
    public PeopleTable() {
        super("people");
    }

    @Override
    protected Person rowToType(DbRow row) {
        return new Person(row.getString("name"), row.getInt("age"));
    }

    @Override
    protected KeyValueSet typeToRow(Person person) {
        return KeyValueSet.builder()
                .key("name").value(person.getTitle())
                .key("age").value(person.getAge())
                .build();
    }
}
