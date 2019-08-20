package me.piggypiglet.test;

import me.piggypiglet.framework.utils.SearchUtils;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class Person implements SearchUtils.Searchable {
    private final String name;
    private final int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return String.format("Person(name=%s,age=%s)", name, age);
    }
}
