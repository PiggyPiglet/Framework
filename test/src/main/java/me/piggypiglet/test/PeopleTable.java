package me.piggypiglet.test;

import co.aikar.idb.DbRow;
import me.piggypiglet.framework.mysql.components.row.objects.KeyValueSet;
import me.piggypiglet.framework.mysql.table.Table;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class PeopleTable extends Table<Person> {
    public PeopleTable() {
        super("people");
    }

    @Override
    protected KeyValueSet typeToRow(Person person) {
        return KeyValueSet.builder()
                .key("name").value(person.getName())
                .key("age").value(person.getAge())
                .build();
    }

    @Override
    protected Person rowToType(DbRow row) {
        return new Person(row.getString("name"), row.getInt("age"));
    }
}
