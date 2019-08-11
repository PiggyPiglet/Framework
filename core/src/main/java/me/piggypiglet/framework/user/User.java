package me.piggypiglet.framework.user;

import java.util.List;
import java.util.stream.Collectors;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public abstract class User {
    private final String name;
    private final String id;
    private final List<String> permissions;

    public User(String name, String id, List<String> permissions) {
        this.name = name;
        this.id = id;
        this.permissions = permissions.stream().map(String::toLowerCase).collect(Collectors.toList());
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public boolean hasPermission(String permission) {
        return permissions.contains(permission.toLowerCase());
    }

    protected abstract void sendMessage(String message);

    public void sendMessage(String message, Object... vars) {
        sendMessage(String.format(message, vars));
    }
}
