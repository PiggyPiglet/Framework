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

    /**
     * Provide basic info to the superclass
     * @param name Name of the user
     * @param id ID of the user
     * @param permissions Permissions the user has
     */
    protected User(String name, String id, List<String> permissions) {
        this.name = name;
        this.id = id;
        this.permissions = permissions.stream().map(String::toLowerCase).collect(Collectors.toList());
    }

    /**
     * Get the user's name
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * Get the user's unique identifier
     * @return String
     */
    public String getId() {
        return id;
    }

    /**
     * Does the user have a specific permission
     * @param permission String
     * @return Boolean
     */
    public boolean hasPermission(String permission) {
        return permissions.contains(permission.toLowerCase());
    }

    /**
     * Implementation of sending the user a message
     * @param message Message to send
     */
    protected abstract void sendMessage(String message);

    /**
     * Send the user a message
     * @param message Message to send
     * @param vars Vars to replace in the message (uses String#format)
     */
    public void sendMessage(String message, Object... vars) {
        sendMessage(String.format(message, vars));
    }
}
