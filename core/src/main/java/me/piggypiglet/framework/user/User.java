package me.piggypiglet.framework.user;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public abstract class User {
    private final String name;
    private final String id;

    /**
     * Provide basic info to the superclass
     * @param name Name of the user
     * @param id ID of the user
     */
    protected User(String name, String id) {
        this.name = name;
        this.id = id;
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
    public abstract boolean hasPermission(String permission);

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
